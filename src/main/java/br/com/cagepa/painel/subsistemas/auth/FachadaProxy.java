package br.com.cagepa.painel.subsistemas.auth;

import br.com.cagepa.painel.core.dtos.ClienteDTO;
import br.com.cagepa.painel.core.excecoes.AutenticacaoException;
import br.com.cagepa.painel.core.fachada.FachadaPainel;
import br.com.cagepa.painel.core.fachada.IFachada;
import br.com.cagepa.painel.infra.Logger;

import java.util.List;

public class FachadaProxy implements IFachada {
    private final FachadaPainel fachadaReal;

    public FachadaProxy() {
        this.fachadaReal = FachadaPainel.getInstance();
    }

    //Metódo auxiliar para validar token
    private void verificarPermissao(String token) {
        if(!AuthManager.getInstance().validarToken(token)) {
            Logger.getInstance().logErro("Acesso Negado: Token inválido.");
            throw new AutenticacaoException("Token invalido, faça login novamente.");
        }
    }

    @Override
    public JWTToken login(String email, String senha) {
        //Login não precisa de token
        return fachadaReal.login(email, senha);
    }

    @Override
    public void cadastrarCliente(String nome, String cpf, String token) {
        verificarPermissao(token);
        fachadaReal.cadastrarCliente(nome, cpf, token);
    }

    @Override
    public List<ClienteDTO> listarClientes(String token) {
        verificarPermissao(token);
        return fachadaReal.listarClientes(token);
    }
}
