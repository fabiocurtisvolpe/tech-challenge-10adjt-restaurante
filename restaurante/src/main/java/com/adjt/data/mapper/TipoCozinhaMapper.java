package com.adjt.data.mapper;

import com.adjt.core.model.TipoCozinha;
import com.adjt.data.entity.TipoCozinhaEntity;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class TipoCozinhaMapper {

    public TipoCozinha toModel(TipoCozinhaSource source) {
        if (source == null) {
            return null;
        }

        TipoCozinha tipoCozinha = new TipoCozinha();
        tipoCozinha.setId(source.getId());
        tipoCozinha.setNome(source.getNome());
        tipoCozinha.setDescricao(source.getDescricao());

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
