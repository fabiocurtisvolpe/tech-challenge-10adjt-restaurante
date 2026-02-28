package com.adjt.core.usecase.cliente;

import com.adjt.core.model.Cliente;
import com.adjt.core.port.ClientePort;
import com.adjt.core.validator.ClienteValidator;
import com.adjt.core.validator.EnderecoValidator;
import jakarta.enterprise.context.ApplicationScoped;


@ApplicationScoped
public class AtualizarClienteUseCase {

    private final ClientePort<Cliente> clientePort;

    public AtualizarClienteUseCase(ClientePort<Cliente> clientePort) {
        this.clientePort = clientePort;
    }

    public Cliente run(Cliente cliente) {

        ClienteValidator.validarId(cliente);
        ClienteValidator.camposObrigatorio(cliente);

        EnderecoValidator.validarId(cliente.getEnderecos());
        EnderecoValidator.camposObrigatorio(cliente.getEnderecos());

        return clientePort.atualizar(cliente);
    }
}
