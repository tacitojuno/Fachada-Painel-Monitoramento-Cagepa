package br.com.cagepa.painel;

import br.com.cagepa.painel.core.dtos.ClienteDTO;
import br.com.cagepa.painel.core.dtos.ConsumoDTO;
import br.com.cagepa.painel.core.fachada.IFachada;
import br.com.cagepa.painel.subsistemas.auth.FachadaProxy;
import br.com.cagepa.painel.subsistemas.auth.JWTToken;

import java.util.List;
import java.util.Scanner;

public class Main {

    //O Proxy é estático para ser acessado por toda a Main
    private static final IFachada sistema = new FachadaProxy();
    private static String tokenAtual = null;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean rodando = true;

        System.out.println("=========================================");
        System.out.println("   PAINEL DE MONITORAMENTO CAGEPA (CLI)  ");
        System.out.println("=========================================");

        while (rodando) {
            exibirMenu();
            System.out.print("> ");
            String opcao = scanner.nextLine();

            try {
                switch (opcao) {
                    case "1":
                        realizarLogin(scanner);
                        break;
                    case "2":
                        cadastrarCliente(scanner);
                        break;
                    case "3":
                        listarClientes();
                        break;
                    case "4":
                        processarImagem(scanner);
                        break;
                    case "5":
                        monitorarRede(scanner);
                        break;
                    case "6":
                        vincularHidrometro(scanner);
                        break;
                    case "7":
                        consultarHistorico(scanner);
                        break;
                    case "8":
                        removerCliente(scanner);
                        break;
                    case "0":
                        rodando = false;
                        System.out.println("Encerrando sistema...");
                        break;
                    default:
                        System.out.println("Opção inválida!");
                }
            } catch (Exception e) {
                System.out.println("ERRO: " + e.getMessage());
            }
            System.out.println("-----------------------------------------");
        }
        scanner.close();
    }

    private static void exibirMenu() {
        String statusAuth = (tokenAtual != null) ? "[LOGADO]" : "[DESCONECTADO]";
        System.out.println("\nSTATUS: " + statusAuth);
        System.out.println("1. Login (Admin)");
        System.out.println("2. Cadastrar Cliente");
        System.out.println("3. Listar Clientes");
        System.out.println("4. Processar Leitura Manual (OCR/Arquivo)");
        System.out.println("5. Monitorar Rede");
        System.out.println("6. Vincular Hidrômetro a Cliente");
        System.out.println("7. Consultar Histórico de Consumo");
        System.out.println("8. Remover Cliente");
        System.out.println("0. Sair");
        System.out.print("Escolha uma opção: ");
    }

    private static void realizarLogin(Scanner scanner) {
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Senha: ");
        String senha = scanner.nextLine();

        //Chama a Fachada (via Proxy, mas login passa direto)
        JWTToken token = sistema.login(email, senha);
        tokenAtual = token.getTokenString();
        System.out.println("SUCESSO: Login realizado!");
    }

    private static void cadastrarCliente(Scanner scanner) {
        if (tokenAtual == null) {
            System.out.println("AVISO: Faça login primeiro!");
            return;
        }
        System.out.print("Nome do Cliente: ");
        String nome = scanner.nextLine();
        System.out.print("CPF/CNPJ: ");
        String cpf = scanner.nextLine();

        //Chama a Fachada (Proxy valida o token)
        sistema.cadastrarCliente(nome, cpf, tokenAtual);
        System.out.println("SUCESSO: Cliente enviado para cadastro.");
    }

    private static void listarClientes() {
        if (tokenAtual == null) {
            System.out.println("AVISO: Faça login primeiro!");
            return;
        }
        List<ClienteDTO> clientes = sistema.listarClientes(tokenAtual);
        if (clientes.isEmpty()) {
            System.out.println("Nenhum cliente encontrado (memória vazia).");
        } else {
            System.out.println("\n--- Lista de Clientes ---");
            for (ClienteDTO c : clientes) {
                System.out.println("Nome: " + c.nome() + " | Doc: " + c.cpfCnpj());
            }
        }
    }

    private static void processarImagem(Scanner scanner) {
        if (tokenAtual == null) {
            System.out.println("AVISO: Faça login primeiro!");
            return;
        }
        System.out.print("Caminho do arquivo de imagem: ");
        String caminho = scanner.nextLine();
        System.out.print("Matrícula do SHA: ");
        String matricula = scanner.nextLine();

        System.out.println("Iniciando processamento...");
        sistema.processarLeituraManual(caminho, matricula, tokenAtual);
    }

    private static void monitorarRede(Scanner scanner) {
        if (tokenAtual == null) {
            System.out.println("AVISO: Faça login primeiro!");
            return;
        }
        System.out.println("\n--- INICIANDO VARREDURA DE MONITORAMENTO ---");
        //Aqui poderia colocar um loop (while) com Thread.sleep(5000) para fazer automático a cada 5s
        //Para testes, chamar uma vez é mais seguro.
        sistema.monitorarRede(tokenAtual);
        System.out.println("--- FIM DA VARREDURA ---");
    }

    private static void vincularHidrometro(Scanner scanner) {
        if (tokenAtual == null) {
            System.out.println("AVISO: Faça login primeiro!");
            return;
        }
        System.out.print("CPF do Cliente Dono: ");
        String cpf = scanner.nextLine();

        System.out.print("Matrícula do SHA (Ex: SHA-01): ");
        String matricula = scanner.nextLine();

        System.out.print("Caminho da Pasta das Imagens: ");
        String caminho = scanner.nextLine(); //Caminho da Pasta!

        sistema.vincularHidrometro(cpf, matricula, caminho, tokenAtual);
        System.out.println("SUCESSO: Vinculação realizada.");
    }

    private static void consultarHistorico(Scanner scanner) {
        if (tokenAtual == null) {
            System.out.println("AVISO: Faça login primeiro!");
            return;
        }
        System.out.print("Matrícula do SHA para consulta: ");
        String matricula = scanner.nextLine();

        List<ConsumoDTO> historico = sistema.consultarHistoricoSHA(matricula, tokenAtual);

        if (historico.isEmpty()) {
            System.out.println("Nenhum histórico encontrado (ou SHA não existe). Execute a opção 5 primeiro.");
        } else {
            System.out.println("\n--- Histórico de Consumo: " + matricula + " ---");
            for (ConsumoDTO c : historico) {
                System.out.println("Data: " + c.dataHora() + " | Leitura: " + c.leituraAtual() + "m3");
            }
        }
    }

    private static void removerCliente(Scanner scanner) {
        if (tokenAtual == null) {
            System.out.println("AVISO: Faça login primeiro!");
            return;
        }
        System.out.print("CPF do Cliente a remover: ");
        String cpf = scanner.nextLine();

        sistema.removerCliente(cpf, tokenAtual);
        System.out.println("SUCESSO: Cliente removido.");
    }
}