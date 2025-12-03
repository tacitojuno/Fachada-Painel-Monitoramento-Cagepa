package br.com.cagepa.painel.subsistemas.notificacao;

import br.com.cagepa.painel.core.entidades.Leitura;
import br.com.cagepa.painel.infra.Logger;

public class RegraPreventiva  extends RegraAlertaHandler {

    @Override
    public void verificar(Leitura leitura, double limiteMensal) {
        if(leitura.getValorM3() >= (limiteMensal * 0.8) && leitura.getValorM3() < limiteMensal){
            Logger.getInstance().log("CHAIN: [Alerta Preventivo] Consumo atingiu 80% do limite (" + leitura.getValorM3() + "m3)");
            //Aqui chamaria o notificador, ex: email, sms, push, etc.
        }
        chamarProximo(leitura, limiteMensal);
    }
}
