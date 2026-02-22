package com.adjt.core.usecase.cliente;

import com.adjt.core.model.Cliente;
import com.adjt.core.port.ClientePort;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ObterPorCpfClienteUseCase {

    private final ClientePort<Cliente> clientePort;

    public ObterPorCpfClienteUseCase(ClientePort<Cliente> clientePort) {
        this.clientePort = clientePort;
    }

    public Cliente run(String cpf) {
        return clientePort.obterPorCpf(cpf);
    }
}
