package br.com.cagepa.painel;

import br.com.cagepa.painel.core.dtos.ClienteDTO;
import br.com.cagepa.painel.core.excecoes.AutenticacaoException;
import br.com.cagepa.painel.core.fachada.IFachada;
import br.com.cagepa.painel.subsistemas.auth.FachadaProxy;
import br.com.cagepa.painel.subsistemas.auth.JWTToken;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== INICIANDO SISTEMA PAINEL CAGEPA ===");

        //Instancia o Proxy (Ele que controla o acesso à Fachada real)
        IFachada sistema = new FachadaProxy();

        JWTToken tokenAdmin = null;

        //Teste 01: Tentar acessar sem login (Deve Falhar)
        System.out.println("\n--- Teste 01: Acesso sem Token ---");
        try {
            sistema.cadastrarCliente("Indivíduo", "00000000000", "fake_token");
        } catch (AutenticacaoException e) {
            System.out.println("SUCESSO: O sistema bloqueou o acesso não autorizado.");
            System.out.println("Mensagem: " + e.getMessage());
        }

        //Teste 02: Realizar Login
        System.out.println("\n--- Teste 02: Realizando Login ---");
        try {
            tokenAdmin = sistema.login("admin@cagepa.com.br", "admin123");
            System.out.println("Login realizado! Token recebido: " + tokenAdmin.getTokenString().substring(0, 10) + "...");
        } catch (Exception e) {
            System.out.println("Erro fatal no login: " + e.getMessage());
            return;
        }

        //Teste 03: Cadastrar Clientes (Deve Funcionar)
        System.out.println("\n--- Teste 03: Cadastrando Clientes ---");
        try {
            String tokenStr = tokenAdmin.getTokenString();

            sistema.cadastrarCliente("Indivíduo Um", "12345678900", tokenStr);
            sistema.cadastrarCliente("Empresa XYZ", "12345678000199", tokenStr);

            try {
                sistema.cadastrarCliente("Indivíduo Um", "12345678900", tokenStr);
            } catch (IllegalArgumentException e) {
                System.out.println("Validação funcionando: " + e.getMessage());
            }
        } catch (Exception e) {
            System.out.println("Erro ao cadastrar: " + e.getMessage());
        }

        //Teste 04: Listar Dados
        System.out.println("\n--- Teste 04: Listagem Geral ---");
        List<ClienteDTO> clientes = sistema.listarClientes(tokenAdmin.getTokenString());

        System.out.println("Total de Clientes: " + clientes.size());
        for(ClienteDTO c : clientes) {
            System.out.println("- Cliente: " + c.nome() + " | Doc: " + c.cpfCnpj());
        }

        //Teste 05: Processamento de Imagem (Template Method + Adapter)
        System.out.println("\n--- Teste 5: Processamento de Imagem ---");

        br.com.cagepa.painel.subsistemas.processamento_imagem.ProcessadorImagemImpl processador = new br.com.cagepa.painel.subsistemas.processamento_imagem.ProcessadorImagemImpl();

        try {
            var leitura = processador.processar("C:/imagens/hidrometro_01.jpg", "SHA-123");
            System.out.println("RESULTADO FINAL: Leitura gerada -> " + leitura.getValorM3() + "m3");
        } catch (Exception e) {
            System.out.println("Erro no processamento: " + e.getMessage());
        }

        //Teste 06: Monitoramento (Strategy + Observer)
        System.out.println("\n--- Teste 6: Monitoramento de Consumo ---");

        br.com.cagepa.painel.subsistemas.monitoramento.AgregadorConsumo agregador = new br.com.cagepa.painel.subsistemas.monitoramento.AgregadorConsumo();

        //Configurando Observer
        agregador.getSubject().adicionarObservador(l -> {
            System.out.println(">> ALERTA (Observer): Nova leitura detectada no SHA " + l.getMatriculaSHA() + " Valor: " + l.getValorM3());
        });

        //Simulando a chegada de uma leitura
        var leituraRecente = new br.com.cagepa.painel.core.entidades.Leitura("SHA-123", 150.0);
        agregador.novaLeituraRecebida(leituraRecente);

        //Testando o Strategy (Cálculo)
        List<br.com.cagepa.painel.core.entidades.Leitura> historico = List.of(
                new br.com.cagepa.painel.core.entidades.Leitura("SHA-123", 100.0),
                new br.com.cagepa.painel.core.entidades.Leitura("SHA-123", 200.0)
        );

        double media = agregador.processarHistorico(historico);
        System.out.println("Cálculo via Strategy (Média): " + media);

        //Teste 07: Sistema de Alertas (Chain of Responsibility + Template Method)
        System.out.println("\n--- Teste 7: Sistema de Alertas ---");

        br.com.cagepa.painel.subsistemas.notificacao.AlertaService alertaService =
                new br.com.cagepa.painel.subsistemas.notificacao.AlertaService();

        //Cenário 1: Consumo normal (sem alertas)
        System.out.println(">> Processando leitura normal (50m3)...");
        alertaService.processarLeitura(new br.com.cagepa.painel.core.entidades.Leitura("SHA-001", 50.0));

        //Cenário 2: Consumo elevado (Chain preventiva)
        System.out.println("\n>> Processando leitura alta (85m3)...");
        alertaService.processarLeitura(new br.com.cagepa.painel.core.entidades.Leitura("SHA-002", 85.0));

        //Cenário 3: Consumo crítico (Chain crítica + Notificação)
        System.out.println("\n>> Processando leitura crítica (110m3)...");
        alertaService.processarLeitura(new br.com.cagepa.painel.core.entidades.Leitura("SHA-003", 110.0));

        System.out.println("\n=== FIM DOS TESTES ===");
    }

}