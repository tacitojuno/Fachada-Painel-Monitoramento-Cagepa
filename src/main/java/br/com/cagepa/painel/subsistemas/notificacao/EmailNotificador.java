package br.com.cagepa.painel.subsistemas.notificacao;

import br.com.cagepa.painel.infra.Logger;

public class EmailNotificador extends NotificadorTemplate {

    @Override
    protected String formatarMensagem(String crua) {
        return "ASSUNTO: Alerta Cagepa\nCORPO: " + crua + "\nRodapé: Não responda.";
    }

    @Override
    protected void realizarEnvio(String dest, String msg) {
        Logger.getInstance().log("NOTIFICACAO (Email) enviado para " + dest + ":\n" + msg);
    }
}
