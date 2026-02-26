package com.adjt.rest.mapper;

import com.adjt.core.model.Cardapio;
import com.adjt.core.model.TipoCozinha;
import com.adjt.data.mapper.TipoCozinhaMapper;
import com.adjt.rest.dto.request.TipoCozinhaRequest;
import com.adjt.rest.dto.response.TipoCozinhaResponse;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class TipoCozinhaRestMapper {

    private final TipoCozinhaMapper tipoCozinhaMapper;

    public TipoCozinhaRestMapper(TipoCozinhaMapper tipoCozinhaMapper) {
        this.tipoCozinhaMapper = tipoCozinhaMapper;
    }

    public TipoCozinha toModel(TipoCozinhaRequest request) {
        return tipoCozinhaMapper.toModel(request);
    }

    public TipoCozinhaResponse toResponse(TipoCozinha model) {
        if (model == null) {
            return null;
        }

        TipoCozinhaResponse response = new TipoCozinhaResponse();
        response.id = model.getId();
        response.nome = model.getNome();
        response.descricao = model.getDescricao();

        return response;
    }
}
