package com.adjt.core.usecase.cliente;

import com.adjt.core.model.Cliente;
import com.adjt.core.model.Perfil;
import com.adjt.core.port.ClientePort;
import com.adjt.core.port.PerfilPort;
import com.adjt.core.validator.ClienteValidator;
import com.adjt.core.validator.EnderecoValidator;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CriarClienteUseCase {

    private final ClientePort<Cliente> clientePort;
    private final PerfilPort<Perfil> perfilPort;

    public CriarClienteUseCase(ClientePort<Cliente> clientePort,
                                PerfilPort<Perfil> perfilPort) {
        this.clientePort = clientePort;
        this.perfilPort = perfilPort;
    }

    public Cliente run(Cliente cliente) {
        ClienteValidator.camposObrigatorio(cliente);
        EnderecoValidator.camposObrigatorio(cliente.getEnderecos());

        Perfil perfil = perfilPort.obterPorNome("ROLE_CLIENTE");
        cliente.setPerfil(perfil);

        return clientePort.criar(cliente);
    }
}
