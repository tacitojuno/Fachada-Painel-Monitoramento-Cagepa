package br.com.cagepa.painel.core.entidades;

import java.time.LocalDateTime;

public class Leitura {
    private String matriculaSHA;
    private double valorM3;
    private LocalDateTime dataHora;

    public Leitura(String matriculaSHA, double valorM3) {
        this.matriculaSHA = matriculaSHA;
        this.valorM3 = valorM3;
        this.dataHora = LocalDateTime.now();
    }

    public String getMatriculaSHA() {
        return matriculaSHA;
    }

    public double getValorM3() {
        return valorM3;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }
}
