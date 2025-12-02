package br.com.cagepa.painel.subsistemas.monitoramento;

import br.com.cagepa.painel.core.entidades.Leitura;
import br.com.cagepa.painel.infra.Logger;
import java.util.ArrayList;
import java.util.List;

public class LeituraSubject {
    private final List<IMonitoramentoObserver> observadores = new ArrayList<>();

    public void adicionarObservador(IMonitoramentoObserver obs) {
        observadores.add(obs);
    }

    public void notificarObservadores(Leitura leitura) {
        Logger.getInstance().log("OBSERVER: Notificando " + observadores.size() + " sistemas interessados sobre nova leitura.");
        for (IMonitoramentoObserver obs : observadores) {
            obs.notificarNovaLeitura(leitura);
        }
    }
}
