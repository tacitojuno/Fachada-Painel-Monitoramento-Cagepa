package br.com.cagepa.painel.subsistemas.notificacao;

import br.com.cagepa.painel.core.entidades.Leitura;

public abstract class RegraAlertaHandler {

    protected RegraAlertaHandler proximo;

    //Define próximo passo da corrente
    public void setProximo(RegraAlertaHandler proximo) {
        this.proximo = proximo;
    }

    //Processa e passa adiante
    public abstract void verificar(Leitura leitura, double limiteMensal);

    //Passar para o próximo se ele existir
    protected void chamarProximo(Leitura leitura, double limite) {
        if (proximo != null) {
            proximo.verificar(leitura, limite);
        }
    }
}
