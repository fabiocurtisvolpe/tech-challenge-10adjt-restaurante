package com.adjt.core.usecase.perfil;

import com.adjt.core.model.Perfil;
import com.adjt.core.port.PerfilPort;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ObterPorIdPerfilUseCase {

    private final PerfilPort<Perfil> perfilPort;

    public ObterPorIdPerfilUseCase(PerfilPort<Perfil> perfilPort) {
        this.perfilPort = perfilPort;
    }

    public Perfil run(Long id) {
        return perfilPort.obterPorId(id);
    }

}
