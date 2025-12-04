package br.com.cagepa.painel.core.entidades;

import java.time.LocalDateTime;

public class Alerta {
    private String matriculaSHA;
    private String mensagem;
    private boolean isCritico;
    private LocalDateTime dataHora;

    public Alerta(String matriculaSHA, String mensagem, boolean isCritico) {
        this.matriculaSHA = matriculaSHA;
        this.mensagem = mensagem;
        this.isCritico = isCritico;
        this.dataHora = LocalDateTime.now();
    }

    public String getMensagem() { return mensagem; }
    public boolean isCritico() { return isCritico; }
    public String getMatriculaSHA() { return matriculaSHA; }
}
