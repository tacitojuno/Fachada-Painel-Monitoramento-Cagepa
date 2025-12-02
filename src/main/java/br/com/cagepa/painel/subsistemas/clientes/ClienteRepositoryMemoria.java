package br.com.cagepa.painel.subsistemas.clientes;

import br.com.cagepa.painel.core.entidades.Cliente;
import br.com.cagepa.painel.infra.Logger;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ClienteRepositoryMemoria implements ClienteRepository {

    //Simula Banco de Dados em memória
    private final List<Cliente> bancoDeDados = new ArrayList<>();

    @Override
    public void salvar(Cliente cliente) {
        bancoDeDados.add(cliente);
        Logger.getInstance().log("Repository: Cliente " + cliente.getNome() + " salvo na memória.");
    }

    @Override
    public Optional<Cliente> buscarPorCpf(String cpf) {
        return bancoDeDados.stream()
                .filter(c -> c.getCpfCnpj().equals(cpf))
                .findFirst();
    }

    @Override
    public List<Cliente> listarTodos() {
        return new ArrayList<>(bancoDeDados);
    }

    @Override
    public void deletar(String cpf) {
        bancoDeDados.removeIf(c -> c.getCpfCnpj().equals(cpf));
        Logger.getInstance().log("Repository: Cliente com CPF " + cpf + " deletado da memória.");
    }
}
