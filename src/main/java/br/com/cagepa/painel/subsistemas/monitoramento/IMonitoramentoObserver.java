package br.com.cagepa.painel.subsistemas.monitoramento;

import br.com.cagepa.painel.core.entidades.Leitura;

//Observer
public interface IMonitoramentoObserver {
    void notificarNovaLeitura(Leitura leitura);
}
