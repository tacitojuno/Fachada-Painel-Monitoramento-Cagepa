package br.com.cagepa.painel.subsistemas.clientes;

import br.com.cagepa.painel.core.dtos.ClienteDTO;
import br.com.cagepa.painel.core.entidades.Cliente;
import br.com.cagepa.painel.infra.Logger;
import br.com.cagepa.painel.subsistemas.monitoramento.AgregadorConsumo;
import br.com.cagepa.painel.subsistemas.processamento_imagem.ProcessadorImagemImpl;

import java.io.File;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class GestorClientes {

    private final ClienteRepository repository;
    private final ClienteFactory factory;

    public GestorClientes() {
        this.repository = new ClienteRepositoryMemoria();
        this.factory = new ClienteFactory();
    }

    public void cadastrarCliente(String nome, String cpf, String email) {
        if(repository.buscarPorCpf(cpf).isPresent()) {
            throw new IllegalArgumentException("Cliente já existe!");
        }
        Cliente novoCliente = factory.criarCliente(nome, cpf, email);
        repository.salvar(novoCliente);
        Logger.getInstance().log("Cliente cadastrado com sucesso.");
    }

    public void removerCliente(String cpf) {
        if(repository.buscarPorCpf(cpf).isEmpty()) {
            throw new IllegalArgumentException("Cliente não encontrado para remoção.");
        }
        repository.deletar(cpf);
    }

    public List<ClienteDTO> listarClientes() {
        return repository.listarTodos().stream()
                .map(c -> new ClienteDTO(c.getNome(), c.getCpfCnpj(), c.getHidrometros().size()))
                .collect(Collectors.toList());
    }

    public void processarVarredura(String token, ProcessadorImagemImpl processador, AgregadorConsumo monitoramento) {
        List<Cliente> todosClientes = repository.listarTodos();

        for (Cliente cliente : todosClientes) {
            for (var sha : cliente.getHidrometros()) {
                File diretorio = new File(sha.getDiretorioImagens());

                if (diretorio.exists() && diretorio.isDirectory()) {
                    File[] imagens = diretorio.listFiles((dir, name) -> name.toLowerCase().endsWith(".jpg") || name.toLowerCase().endsWith(".jpeg"));

                    if (imagens != null && imagens.length > 0) {
                        Arrays.sort(imagens, Comparator.comparingLong(File::lastModified).reversed());
                        File imagemMaisRecente = imagens[0];

                        try {
                            //Processa OCR
                            var leitura = processador.processar(imagemMaisRecente.getAbsolutePath(), sha.getMatriculaSHA());

                            //Salva no Histórico do Hidrômetro
                            sha.adicionarLeitura(leitura);

                            //Envia para Monitoramento (Alertas)
                            monitoramento.novaLeituraRecebida(leitura);

                        } catch (Exception e) {
                            Logger.getInstance().logErro("Erro ao processar SHA " + sha.getMatriculaSHA() + ": " + e.getMessage());
                        }
                    }
                }
            }
        }
    }

    public void vincularHidrometro(String cpfCnpj, String matricula, String caminhoPasta) {
        var clienteOpt = repository.buscarPorCpf(cpfCnpj);
        if (clienteOpt.isPresent()) {
            var novoSHA = new br.com.cagepa.painel.core.entidades.Hidrometro(matricula, caminhoPasta);
            clienteOpt.get().adicionarHidrometro(novoSHA);
            Logger.getInstance().log("GESTOR: SHA " + matricula + " vinculado ao cliente.");
        } else {
            throw new IllegalArgumentException("Cliente não encontrado.");
        }
    }

    //Busca o hidrômetro para exibir histórico
    public br.com.cagepa.painel.core.entidades.Hidrometro buscarHidrometro(String matricula) {
        return repository.listarTodos().stream()
                .flatMap(c -> c.getHidrometros().stream())
                .filter(h -> h.getMatriculaSHA().equals(matricula))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Hidrômetro não encontrado: " + matricula));
    }
}