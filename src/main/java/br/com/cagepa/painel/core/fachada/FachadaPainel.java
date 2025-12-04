package br.com.cagepa.painel.core.fachada;

import br.com.cagepa.painel.core.dtos.ClienteDTO;
import br.com.cagepa.painel.core.entidades.Leitura;
import br.com.cagepa.painel.infra.Logger;
import br.com.cagepa.painel.subsistemas.auth.AuthManager;
import br.com.cagepa.painel.subsistemas.auth.JWTToken;
import br.com.cagepa.painel.subsistemas.clientes.GestorClientes;
import br.com.cagepa.painel.subsistemas.monitoramento.AgregadorConsumo;
import br.com.cagepa.painel.subsistemas.notificacao.AlertaService;
import br.com.cagepa.painel.subsistemas.processamento_imagem.ProcessadorImagemImpl;

import java.util.List;

public class FachadaPainel implements IFachada {

    private static FachadaPainel instance;

    //Todos os Subsistemas
    private final GestorClientes gestorClientes;
    private final AgregadorConsumo monitoramento;
    private final AlertaService alertaService;
    private final ProcessadorImagemImpl processadorOCR;

    private FachadaPainel() {
        //Instancia os subsistemas
        this.gestorClientes = new GestorClientes();
        this.monitoramento = new AgregadorConsumo();
        this.alertaService = new AlertaService();
        this.processadorOCR = new ProcessadorImagemImpl();

        //Configura as integrações entre subsistemas via Observer
        this.monitoramento.getSubject().adicionarObservador(leitura -> {
            Logger.getInstance().log("INTEGRAÇÃO: Monitoramento repassando leitura para Alertas...");
            alertaService.processarLeitura(leitura);
        });

        Logger.getInstance().log("SISTEMA: Todos os subsistemas inicializados e conectados.");
    }

    public static FachadaPainel getInstance() {
        if(instance == null) instance = new FachadaPainel();
        return instance;
    }

    @Override
    public JWTToken login(String email, String senha) {
        return AuthManager.getInstance().login(email, senha);
    }

    @Override
    public void cadastrarCliente(String nome, String cpf, String token) {
        gestorClientes.cadastrarCliente(nome, cpf, "email@padrao.com");
    }

    @Override
    public List<ClienteDTO> listarClientes(String token) {
        return gestorClientes.listarClientes();
    }

    //Aciona o processamento de imagem e repassa a leitura para o monitoramento
    @Override
    public void processarLeituraManual(String caminhoImagem, String matriculaSHA, String token) {
        //Processa a Imagem (OCR)
        Leitura leitura = processadorOCR.processar(caminhoImagem, matriculaSHA);

        //Repassa a leitura para o Monitoramento
        monitoramento.novaLeituraRecebida(leitura);
    }

    @Override
    public void monitorarRede(String token) {
        Logger.getInstance().log("MONITOR: Iniciando varredura da rede de hidrômetros...");

        //Pega todos os clientes
        List<ClienteDTO> clientes = gestorClientes.listarClientes();

        if (clientes.isEmpty()) {
            Logger.getInstance().log("MONITOR: Nenhum cliente cadastrado para monitorar.");
            return;
        }

        //Para cada cliente, pega os dados reais (acessar a entidade, não o DTO)
        gestorClientes.processarVarredura(token, this.processadorOCR, this.monitoramento);
    }

    @Override
    public void vincularHidrometro(String cpfCnpj, String matricula, String caminhoPasta, String token) {
        gestorClientes.vincularHidrometro(cpfCnpj, matricula, caminhoPasta);
    }

    @Override
    public void removerCliente(String cpf, String token) {
        gestorClientes.removerCliente(cpf);
    }

    @Override
    public List<br.com.cagepa.painel.core.dtos.ConsumoDTO> consultarHistoricoSHA(String matricula, String token) {
        var sha = gestorClientes.buscarHidrometro(matricula);

        //Converte as Entidades Leitura para DTOs para exibir no Main
        return sha.getHistorico().stream()
                .map(l -> new br.com.cagepa.painel.core.dtos.ConsumoDTO(l.getMatriculaSHA(), l.getValorM3(), l.getDataHora().toString()))
                .collect(java.util.stream.Collectors.toList());
    }
}
