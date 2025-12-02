package br.com.cagepa.painel.core.fachada;

import br.com.cagepa.painel.core.dtos.ClienteDTO;
import br.com.cagepa.painel.infra.Logger;
import br.com.cagepa.painel.subsistemas.auth.AuthManager;
import br.com.cagepa.painel.subsistemas.auth.JWTToken;
import br.com.cagepa.painel.subsistemas.clientes.GestorClientes;

import java.util.ArrayList;
import java.util.List;

public class FachadaPainel implements IFachada {

    private static FachadaPainel instancia;

    private final GestorClientes gestorClientes;

    private FachadaPainel() {
        this.gestorClientes = new GestorClientes();
    }

    public static FachadaPainel getInstance() {
        if (instancia == null) {
            instancia = new FachadaPainel();
        }
        return instancia;
    }

    @Override
    public JWTToken login(String email, String senha) {
        //Login direto para o AuthManager
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
}
