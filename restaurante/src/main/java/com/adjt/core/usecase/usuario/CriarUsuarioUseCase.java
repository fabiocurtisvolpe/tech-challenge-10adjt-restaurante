package com.adjt.core.usecase.usuario;

import com.adjt.core.model.Usuario;
import com.adjt.core.port.UsuarioPort;
import com.adjt.core.validator.UsuarioValidator;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CriarUsuarioUseCase {

    private final UsuarioPort<Usuario> usuarioPort;

    public CriarUsuarioUseCase(UsuarioPort<Usuario> usuarioPort) {
        this.usuarioPort = usuarioPort;
    }

    public Usuario run(Usuario usuario) {
        UsuarioValidator.camposObrigatorio(usuario);
        return usuarioPort.criar(usuario);
    }
}
