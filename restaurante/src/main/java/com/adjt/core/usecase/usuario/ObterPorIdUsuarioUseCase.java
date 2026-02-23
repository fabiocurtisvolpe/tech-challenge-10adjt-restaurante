package com.adjt.core.usecase.usuario;

import com.adjt.core.model.Usuario;
import com.adjt.core.port.UsuarioPort;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ObterPorIdUsuarioUseCase {

    private final UsuarioPort<Usuario> usuarioPort;

    public ObterPorIdUsuarioUseCase(UsuarioPort<Usuario> usuarioPort) {
        this.usuarioPort = usuarioPort;
    }

    public Usuario run(Long id) {
        return usuarioPort.obterPorId(id);
    }
}
