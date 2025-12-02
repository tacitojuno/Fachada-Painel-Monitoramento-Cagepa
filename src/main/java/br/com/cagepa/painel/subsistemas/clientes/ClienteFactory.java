package br.com.cagepa.painel.subsistemas.clientes;

import br.com.cagepa.painel.core.entidades.Cliente;

//Padrão Factory: Classe responsável por criar instâncias de Cliente.
public class ClienteFactory {

    public Cliente criarCliente(String nome, String cpfCnpj, String email) {
        if(cpfCnpj == null || cpfCnpj.length() != 11) {
            throw new IllegalArgumentException("CPF/CNPJ inválido.");
        }
        return new Cliente(cpfCnpj, nome, email);
    }
}
