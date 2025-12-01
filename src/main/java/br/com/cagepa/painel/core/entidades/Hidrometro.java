package br.com.cagepa.painel.core.entidades;

public class Hidrometro {
    private String matriculaSHA;
    private String diretorioImagens;
    private double ultimoConsumoM3;

    public Hidrometro(String matriculaSHA, String diretorioImagens) {
        this.matriculaSHA = matriculaSHA;
        this.diretorioImagens = diretorioImagens;
        this.ultimoConsumoM3 = 0.0;
    }

    public String getMatriculaSHA() {
        return matriculaSHA;
    }

    public String getDiretorioImagens() {
        return diretorioImagens;
    }

    public double getUltimoConsumoM3() {
        return ultimoConsumoM3;
    }

    public void setMatriculaSHA(String matriculaSHA) {
        this.matriculaSHA = matriculaSHA;
    }

    public void setDiretorioImagens(String diretorioImagens) {
        this.diretorioImagens = diretorioImagens;
    }

    public void setUltimoConsumoM3(double ultimoConsumoM3) {
        this.ultimoConsumoM3 = ultimoConsumoM3;
    }
}
