package br.com.cagepa.painel.subsistemas.clientes;

import br.com.cagepa.painel.core.dtos.ClienteDTO;
import br.com.cagepa.painel.core.entidades.Cliente;
import br.com.cagepa.painel.infra.Logger;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class GestorClientes {

    private final ClienteRepository repository;
    private final ClienteFactory factory;

    public GestorClientes() {
        this.repository = new ClienteRepositoryMemoria();
        this.factory = new ClienteFactory();
    }

    public void cadastrarCliente(String nome, String cpf, String email) {
        //1. Verifica se já existe
        if(repository.buscarPorCpf(cpf).isPresent()) {
            Logger.getInstance().logErro("Tentativa de cadastro duplicado: " + cpf);
            throw new IllegalArgumentException("Cliente já existe!");
        }

        //2. Usa a Factory para criar
        Cliente novoCliente = factory.criarCliente(nome, cpf, email);

        //3. Salva no repositório
        repository.salvar(novoCliente);
        Logger.getInstance().log("Cliente cadastrado com sucesso.");
    }

    public List<ClienteDTO> listarClientes() {
        return repository.listarTodos().stream()
                .map(c -> new ClienteDTO(c.getNome(), c.getCpfCnpj(), c.getHidrometros().size()))
                .collect(Collectors.toList());
    }
}
