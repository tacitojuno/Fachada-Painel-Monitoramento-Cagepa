package br.com.cagepa.painel.subsistemas.monitoramento;

import br.com.cagepa.painel.core.entidades.Leitura;
import br.com.cagepa.painel.infra.Logger;
import java.util.List;

public class CalculoMensal implements ICalculoConsumoStrategy {

    @Override
    public double calcular(List<Leitura> leituras) {
        Logger.getInstance().log("STRATEGY: Calculando média de consumo mensal...");
        if(leituras.isEmpty()) return 0.0;

        double soma = leituras.stream().mapToDouble(Leitura::getValorM3).sum();
        return soma / leituras.size();
    }
}
