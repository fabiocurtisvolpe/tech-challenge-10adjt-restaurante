package com.adjt.data.repository.adapter;

import com.adjt.core.model.Perfil;
import com.adjt.core.port.PerfilPort;
import com.adjt.data.entity.PerfilEntity;
import com.adjt.data.mapper.PerfilMapper;
import com.adjt.data.repository.jpa.PerfilRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Optional;

@ApplicationScoped
public class PerfilRepositoryAdapter implements PerfilPort<Perfil> {

    private final PerfilRepository perfilRepository;
    private final PerfilMapper perfilMapper;

    public PerfilRepositoryAdapter(PerfilRepository perfilRepository, PerfilMapper perfilMapper) {
        this.perfilRepository = perfilRepository;
        this.perfilMapper = perfilMapper;
    }

    @Override
    public Perfil obterPorNome(String nome) {
        Optional<PerfilEntity> entity = this.perfilRepository.buscarPorNome(nome);
        return entity.map(this.perfilMapper::toModel).orElse(null);
    }
}
