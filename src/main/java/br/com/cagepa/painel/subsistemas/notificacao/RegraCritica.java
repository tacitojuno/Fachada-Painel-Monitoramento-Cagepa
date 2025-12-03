package br.com.cagepa.painel.subsistemas.notificacao;

import br.com.cagepa.painel.core.entidades.Leitura;
import br.com.cagepa.painel.infra.Logger;

public class RegraCritica extends RegraAlertaHandler {

    @Override
    public void verificar(Leitura leitura, double limiteMensal) {
        if(leitura.getValorM3() >= limiteMensal) {
            Logger.getInstance().logErro("CHAIN: [ALERTA CRÍTICO] Limite ultrapassado! " + leitura.getValorM3() + "m3 > " + limiteMensal);
        }
        chamarProximo(leitura, limiteMensal);
    }
}
