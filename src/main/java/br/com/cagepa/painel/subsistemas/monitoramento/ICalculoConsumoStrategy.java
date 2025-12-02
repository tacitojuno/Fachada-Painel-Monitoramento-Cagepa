package br.com.cagepa.painel.subsistemas.monitoramento;

import br.com.cagepa.painel.core.entidades.Leitura;
import java.util.List;

//Strategy
public interface ICalculoConsumoStrategy {
    double calcular(List<Leitura> leituras);
}
