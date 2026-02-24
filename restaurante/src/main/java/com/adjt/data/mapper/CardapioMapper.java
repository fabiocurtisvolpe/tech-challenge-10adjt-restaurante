package com.adjt.data.mapper;

import com.adjt.core.model.Cardapio;
import com.adjt.data.entity.CardapioEntity;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CardapioMapper {

    public Cardapio toModel(CardapioSource source) {
        if (source == null) {
            return null;
        }

        Cardapio cardapio = new Cardapio();
        cardapio.setId(source.getId());
        cardapio.setNome(source.getNome());
        cardapio.setDescricao(source.getDescricao());
        cardapio.setPreco(source.getPreco());
        cardapio.setFoto(source.getFoto());
        cardapio.setDisponivel(source.getDisponivel());

        return cardapio;
    }

    public CardapioEntity toEntity(Cardapio model) {
        if (model == null) {
            return null;
        }

        CardapioEntity entity = new CardapioEntity();
        entity.id = model.getId();
        entity.nome = model.getNome();
        entity.descricao = model.getDescricao();
        entity.preco = model.getPreco();
        entity.foto = model.getFoto();
        entity.disponivel = model.getDisponivel();

        return entity;
    }
}
