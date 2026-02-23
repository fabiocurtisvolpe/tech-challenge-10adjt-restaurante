package com.adjt.core.usecase.perfil;

import com.adjt.core.model.Perfil;
import com.adjt.core.port.PerfilPort;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ObterPorNomePerfilUseCase {

    private final PerfilPort<Perfil> perfilPort;

    public ObterPorNomePerfilUseCase(PerfilPort<Perfil> perfilPort) {
        this.perfilPort = perfilPort;
    }

    public Perfil run(String nome) {
        return perfilPort.obterPorNome(nome);
    }

}
