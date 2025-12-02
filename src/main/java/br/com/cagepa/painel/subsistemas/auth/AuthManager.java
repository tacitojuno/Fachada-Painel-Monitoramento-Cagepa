package br.com.cagepa.painel.subsistemas.auth;

import br.com.cagepa.painel.core.excecoes.AutenticacaoException;
import br.com.cagepa.painel.infra.Logger;

import java.util.UUID;

public class AuthManager {
    //Singleton
    private static AuthManager instance;
    private String tokenValidoAtual = null; //Armazena o token ativo para teste

    private AuthManager() {}

    public static AuthManager getInstance() {
        if (instance == null) {
            instance = new AuthManager();
        }
        return instance;
    }

    public JWTToken login(String email, String senha) {
        //Simula validação
        if ("admin@cagepa.com.br".equals(email) && "admin123".equals(senha)) {
            String tokenHash = UUID.randomUUID().toString();
            this.tokenValidoAtual = tokenHash;
            Logger.getInstance().log("Login realizado com sucesso: " + email);
            return new JWTToken(tokenHash, email);
        }
        Logger.getInstance().logErro("Login invalido: " + email);
        throw new AutenticacaoException("Credenciais invalidas");
    }

    public boolean validarToken(String token) {
        return token != null && token.equals(this.tokenValidoAtual);
    }

    public void logout() {
        this.tokenValidoAtual = null;
        Logger.getInstance().log("Logout realizado com sucesso.");
    }
}
