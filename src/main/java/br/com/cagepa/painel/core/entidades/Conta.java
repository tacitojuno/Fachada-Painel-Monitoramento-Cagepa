package br.com.cagepa.painel.core.entidades;
//Representa uma conta de cliente
public class Conta {
    private String idConta;
    private String endereco;
    private String cep;

    public Conta(String idConta, String endereco, String cep) {
        this.idConta = idConta;
        this.endereco = endereco;
        this.cep = cep;
    }

    public String getEndereco() { return endereco; }
}
