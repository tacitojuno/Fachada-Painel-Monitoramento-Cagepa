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

        //1. Instancia o Proxy
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

        System.out.println("\n=== FIM DOS TESTES ===");
    }

}