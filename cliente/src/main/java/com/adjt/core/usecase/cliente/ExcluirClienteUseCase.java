package com.adjt.core.usecase.cliente;

import com.adjt.core.model.Cliente;
import com.adjt.core.port.ClientePort;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ExcluirClienteUseCase {

    private final ClientePort<Cliente> clientePort;

    public ExcluirClienteUseCase(ClientePort<Cliente> clientePort) {
        this.clientePort = clientePort;
    }

    public boolean run(Long id) {
        return clientePort.excluir(id);
    }
}
