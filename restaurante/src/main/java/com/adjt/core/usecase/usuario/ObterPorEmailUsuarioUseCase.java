package com.adjt.core.usecase.usuario;

import com.adjt.core.model.Usuario;
import com.adjt.core.port.UsuarioPort;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ObterPorEmailUsuarioUseCase {

    private final UsuarioPort<Usuario> usuarioPort;

    public ObterPorEmailUsuarioUseCase(UsuarioPort<Usuario> usuarioPort) {
        this.usuarioPort = usuarioPort;
    }

    public Usuario run(String email) {
        return usuarioPort.obterPorEmail(email);
    }
}
