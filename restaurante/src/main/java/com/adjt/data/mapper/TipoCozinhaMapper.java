package com.adjt.data.mapper;

import com.adjt.core.model.TipoCozinha;
import com.adjt.data.entity.TipoCozinhaEntity;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class TipoCozinhaMapper {

    public TipoCozinha toModel(TipoCozinhaEntity entity) {
        if (entity == null) {
            return null;
        }

        TipoCozinha tipoCozinha = new TipoCozinha();
        tipoCozinha.setId(entity.id);
        tipoCozinha.setNome(entity.nome);
        tipoCozinha.setDescricao(entity.descricao);

        return tipoCozinha;
    }

    public TipoCozinhaEntity toEntity(TipoCozinha model) {
        if (model == null) {
            return null;
        }

        TipoCozinhaEntity entity = new TipoCozinhaEntity();
        entity.id = model.getId();
        entity.nome = model.getNome();
        entity.descricao = model.getDescricao();

        return entity;
    }
}
