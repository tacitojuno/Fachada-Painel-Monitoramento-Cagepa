package br.com.cagepa.painel.subsistemas.notificacao;

import br.com.cagepa.painel.core.entidades.Leitura;

public class AlertaService {

    private final RegraAlertaHandler chain;
    private final NotificadorTemplate notificador;

    public AlertaService() {
        RegraPreventiva preventiva = new RegraPreventiva();
        RegraCritica critica = new RegraCritica();

        preventiva.setProximo(critica);
        this.chain = preventiva;

        this.notificador = new EmailNotificador();
    }

    public void processarLeitura(Leitura leitura) {
        //Simulando limite fixo para teste
        double limite = 100.0;

        //Dispara corrente
        chain.verificar(leitura, limite);

        //Se for crítico, notifica
        if (leitura.getValorM3() > limite) {
            notificador.enviarNotificacao("admin@cagepa.com.br", "O hidrômetro " + leitura.getMatriculaSHA() + " estourou o consumo!");
        }
    }
}
