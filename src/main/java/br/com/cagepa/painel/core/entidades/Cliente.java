package br.com.cagepa.painel.core.entidades;

import java.util.ArrayList;
import java.util.List;

public class Cliente {
    private String cpfCnpj;
    private String nome;
    private String email;
    private List<Hidrometro> hidrometros;

    public Cliente(String cpfCnpj, String nome, String email) {
        this.cpfCnpj = cpfCnpj;
        this.nome = nome;
        this.email = email;
        this.hidrometros = new ArrayList<>();
    }

    public void adicionarHidrometro(Hidrometro sha) {
        this.hidrometros.add(sha);
    }

    public String getCpfCnpj() {
        return cpfCnpj;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public List<Hidrometro> getHidrometros() {
        return hidrometros;
    }
}
