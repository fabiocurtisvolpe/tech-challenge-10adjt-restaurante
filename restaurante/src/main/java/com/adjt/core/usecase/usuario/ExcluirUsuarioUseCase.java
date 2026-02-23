package com.adjt.core.usecase.usuario;

import com.adjt.core.model.Usuario;
import com.adjt.core.port.UsuarioPort;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ExcluirUsuarioUseCase {

    private final UsuarioPort<Usuario> usuarioPort;

    public ExcluirUsuarioUseCase(UsuarioPort<Usuario> usuarioPort) {
        this.usuarioPort = usuarioPort;
    }

    public boolean run(Long id) {
        return usuarioPort.excluir(id);
    }
}
