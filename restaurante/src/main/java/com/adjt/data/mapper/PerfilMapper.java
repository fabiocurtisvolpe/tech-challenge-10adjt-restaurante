package com.adjt.data.mapper;

import com.adjt.core.model.Perfil;
import com.adjt.data.entity.PerfilEntity;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PerfilMapper {

    public Perfil toModel(PerfilEntity entity) {
        if (entity == null) {
            return null;
        }

        Perfil perfil = new Perfil();
        perfil.setId(entity.id);
        perfil.setNome(entity.nome);

        return perfil;
    }

    public PerfilEntity toEntity(Perfil model) {
        if (model == null) {
            return null;
        }

        PerfilEntity entity = new PerfilEntity();
        entity.id = model.getId();
        entity.nome = model.getNome();

        return entity;
    }
}
