package br.com.cagepa.painel.core.fachada;

import br.com.cagepa.painel.core.dtos.ClienteDTO;
import br.com.cagepa.painel.subsistemas.auth.JWTToken;

import java.util.List;

//Definne o contrato da Fachada e do Proxy
public interface IFachada {
    JWTToken login(String email, String senha);
    void cadastrarCliente(String nome, String cpf, String token);
    List<ClienteDTO> listarClientes(String token);
}
