package br.com.cagepa.painel.subsistemas.notificacao;

import br.com.cagepa.painel.infra.Logger;

public abstract class NotificadorTemplate {

    public final void enviarNotificacao(String destinatario, String mensagem) {
        if(validarDestinatario(destinatario)) {
            String msgFormatada = formatarMensagem(mensagem);
            realizarEnvio(destinatario, msgFormatada);
        } else {
            Logger.getInstance().logErro("Destinatário inválido: " + destinatario);
        }
    }

    protected abstract String formatarMensagem(String crua);
    protected abstract void realizarEnvio(String dest, String msg);

    //Opcional: validação padrão do destinatário
    protected boolean validarDestinatario(String dest) {
        return dest != null && !dest.isEmpty();
    }
}
