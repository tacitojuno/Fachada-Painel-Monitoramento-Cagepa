package br.com.cagepa.painel.infra;

import java.time.LocalDateTime;

public class Logger {
    //Padrão Singleton
    private static Logger instancia;

    private Logger() {}

    public static Logger getInstance() {
        if (instancia == null) {
            instancia = new Logger();
        }
        return instancia;
    }

    public void log(String mensagem) {
        System.out.println("[LOG " + LocalDateTime.now() + "] " + mensagem);
    }

    public void logErro(String mensagem) {
        System.err.println("[ERRO " + LocalDateTime.now() + "] " + mensagem);
    }
}
