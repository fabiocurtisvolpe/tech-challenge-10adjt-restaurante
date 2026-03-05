package com.adjt.rest.mapper;

import com.adjt.core.model.Cardapio;
import com.adjt.data.mapper.CardapioMapper;
import com.adjt.rest.dto.request.CardapioRequest;
import com.adjt.rest.dto.response.CardapioResponse;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CardapioRestMapper {

    private final CardapioMapper cardapioMapper;

    public CardapioRestMapper(CardapioMapper cardapioMapper) {
        this.cardapioMapper = cardapioMapper;
    }

    public Cardapio toModel(CardapioRequest request) {
        return cardapioMapper.toModel(request);
    }

    public CardapioResponse toResponse(Cardapio model, Long idRestaurante) {
        if (model == null) {
            return null;
        }

        CardapioResponse response = new CardapioResponse();
        response.id = model.getId();
        response.nome = model.getNome();
        response.descricao = model.getDescricao();
        response.preco = model.getPreco();
        response.foto = model.getFoto();
        response.disponivel = model.getDisponivel();
        response.idRestaurante = idRestaurante;

        return response;
    }
}
