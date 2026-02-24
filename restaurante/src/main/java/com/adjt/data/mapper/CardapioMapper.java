package com.adjt.data.mapper;

import com.adjt.core.model.Cardapio;
import com.adjt.data.entity.CardapioEntity;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CardapioMapper {

    public Cardapio toModel(CardapioEntity entity) {
        if (entity == null) {
            return null;
        }

        Cardapio cardapio = new Cardapio();
        cardapio.setId(entity.id);
        cardapio.setNome(entity.nome);
        cardapio.setDescricao(entity.descricao);
        cardapio.setPreco(entity.preco);
        cardapio.setFoto(entity.foto);
        cardapio.setDisponivel(entity.disponivel);

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
