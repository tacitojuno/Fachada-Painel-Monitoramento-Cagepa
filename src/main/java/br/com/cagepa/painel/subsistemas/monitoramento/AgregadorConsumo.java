package br.com.cagepa.painel.subsistemas.monitoramento;

import br.com.cagepa.painel.core.entidades.Leitura;
import java.util.List;

public class AgregadorConsumo {

    //Composição com Strategy
    private ICalculoConsumoStrategy strategy;
    //Composição com o Subject
    private LeituraSubject subject;

    public AgregadorConsumo() {
        this.subject = new LeituraSubject();
        this.strategy = new CalculoMensal();
    }

    public void setStrategy(ICalculoConsumoStrategy strategy) {
        this.strategy = strategy;
    }

    public LeituraSubject getSubject() {
        return subject;
    }

    public double processarHistorico(List<Leitura> historico) {
        return strategy.calcular(historico);
    }

    public void novaLeituraRecebida(Leitura leitura) {
        //Lógica de salvar no banco de dados, etc.
        subject.notificarObservadores(leitura);
    }
}
