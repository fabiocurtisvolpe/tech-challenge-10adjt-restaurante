package com.adjt.core.usecase.usuario;

import com.adjt.core.model.Usuario;
import com.adjt.core.port.UsuarioPort;
import com.adjt.core.validator.UsuarioValidator;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class AtualizarUsuarioUseCase {

    private final UsuarioPort<Usuario> usuarioPort;

    public AtualizarUsuarioUseCase(UsuarioPort<Usuario> usuarioPort) {
        this.usuarioPort = usuarioPort;
    }

    public Usuario run(Usuario usuario) {

        UsuarioValidator.validarId(usuario);
        UsuarioValidator.camposObrigatorio(usuario);

        return usuarioPort.atualizar(usuario);
    }
}
