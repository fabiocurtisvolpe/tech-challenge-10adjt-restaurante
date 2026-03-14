package com.adjt.data.mapper;

import com.adjt.core.model.ItemPedido;
import com.adjt.data.entity.ItemPedidoEntity;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ItemPedidoMapper {

    public ItemPedido toModel(ItemPedidoEntity entity) {
        if (entity == null) return null;

        ItemPedido model = new ItemPedido();
        model.setId(entity.id);
        model.setIdCardapio(entity.idCardapio);
        model.setQtd(entity.qtd);
        model.setValor(entity.valor);
        return model;
    }

    public ItemPedidoEntity toEntity(ItemPedido model) {
        if (model == null) return null;

        ItemPedidoEntity entity = new ItemPedidoEntity();
        entity.id = model.getId();
        entity.idCardapio = model.getIdCardapio();
        entity.qtd = model.getQtd();
        entity.valor = model.getValor();
        return entity;
    }
}
