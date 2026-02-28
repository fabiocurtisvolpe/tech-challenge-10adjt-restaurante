package com.adjt.core.usecase.cliente;

import com.adjt.core.model.Cliente;
import com.adjt.core.port.ClientePort;
import com.adjt.core.validator.ClienteValidator;
import com.adjt.rest.interceptor.UserContext;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ObterPorIdClienteUseCase {

    private final UserContext userContext;
    private final ClientePort<Cliente> clientePort;

    public ObterPorIdClienteUseCase(ClientePort<Cliente> clientePort,
                                    UserContext userContext) {
        this.clientePort = clientePort;
        this.userContext = userContext;
    }

    public Cliente run(Long id) {
        Cliente resp = clientePort.obterPorId(id);
        ClienteValidator.validarClienteLogado(resp, userContext);
        return resp;
    }
}
