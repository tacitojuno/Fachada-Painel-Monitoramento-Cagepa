package br.com.cagepa.painel.subsistemas.auth;

//Simulação simples de um token
public class JWTToken {
    private String tokenString;
    private String usuarioEmail;

    public JWTToken(String tokenString, String usuarioEmail) {
        this.tokenString = tokenString;
        this.usuarioEmail = usuarioEmail;
    }

    public String getTokenString() {
        return tokenString;
    }
}
