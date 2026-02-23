package com.adjt.core.usecase.usuario;

import com.adjt.core.model.Usuario;
import com.adjt.core.port.UsuarioPort;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ObterPorCpfUsuarioUseCase {

    private final UsuarioPort<Usuario> usuarioPort;

    public ObterPorCpfUsuarioUseCase(UsuarioPort<Usuario> usuarioPort) {
        this.usuarioPort = usuarioPort;
    }

    public Usuario run(String cpf) {
        return usuarioPort.obterPorCpf(cpf);
    }
}
