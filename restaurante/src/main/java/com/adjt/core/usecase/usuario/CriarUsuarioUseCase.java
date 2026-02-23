package com.adjt.core.usecase.usuario;

import com.adjt.core.model.Perfil;
import com.adjt.core.model.Usuario;
import com.adjt.core.port.PerfilPort;
import com.adjt.core.port.UsuarioPort;
import com.adjt.core.validator.UsuarioValidator;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CriarUsuarioUseCase {

    private final UsuarioPort<Usuario> usuarioPort;
    private final PerfilPort<Perfil> perfilPort;

    public CriarUsuarioUseCase(UsuarioPort<Usuario> usuarioPort,
                               PerfilPort<Perfil> perfilPort) {
        this.usuarioPort = usuarioPort;
        this.perfilPort = perfilPort;
    }

    public Usuario run(Usuario usuario, Boolean isDono) {
        UsuarioValidator.camposObrigatorio(usuario);

        String role = isDono ? "ROLE_DONO" : "ROLE_FUNCIONARIO";
        Perfil perfil = perfilPort.obterPorNome(role);
        usuario.setPerfil(perfil);

        return usuarioPort.criar(usuario);
    }
}
