package br.com.cagepa.painel.subsistemas.clientes;

import br.com.cagepa.painel.core.entidades.Cliente;
import java.util.List;
import java.util.Optional;

//Padrão Repository (Interface)
public interface ClienteRepository {
    void salvar(Cliente cliente);
    Optional<Cliente> buscarPorCpf(String cpf);
    List<Cliente> listarTodos();
    void deletar(String cpf);
}
