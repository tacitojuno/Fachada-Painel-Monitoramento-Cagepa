package br.com.cagepa.painel.core.fachada;

import br.com.cagepa.painel.core.dtos.ClienteDTO;
import br.com.cagepa.painel.core.dtos.ConsumoDTO;
import br.com.cagepa.painel.subsistemas.auth.JWTToken;

import java.util.List;

//Definne o contrato da Fachada e do Proxy
public interface IFachada {
    //Auth
    JWTToken login(String email, String senha);
    //Clientes
    void cadastrarCliente(String nome, String cpf, String token);
    void removerCliente(String cpf, String token);
    List<ClienteDTO> listarClientes(String token);
    //Processamento e Monitoramento
    void processarLeituraManual(String camminhoImagem, String matriculaSHA, String token);
    void monitorarRede(String token);
    void vincularHidrometro(String cpfCnpj, String matricula, String caminhoPasta, String token);

    List<ConsumoDTO> consultarHistoricoSHA(String matricula, String token);
}
