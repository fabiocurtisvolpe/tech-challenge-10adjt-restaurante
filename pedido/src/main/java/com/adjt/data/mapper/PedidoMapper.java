package com.adjt.data.mapper;

import com.adjt.core.model.Pedido;
import com.adjt.data.entity.PedidoEntity;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.stream.Collectors;

@ApplicationScoped
public class PedidoMapper {

    @Inject
    ItemPedidoMapper itemMapper;

    public Pedido toModel(PedidoEntity entity) {
        if (entity == null) return null;

        Pedido model = new Pedido();
        model.setId(entity.id);
        model.setDtCadastro(entity.dtCadastro);
        model.setIdCliente(entity.idCliente);
        model.setIdRestaurante(entity.idRestaurante);
        model.setValorTotal(entity.valorTotal);
        model.setStatusCode(entity.statusCode);
        model.setResponseCode(entity.responseCode);

        if (entity.itens != null) {
            model.setItens(entity.itens.stream()
                    .map(itemMapper::toModel)
                    .peek(item -> item.setPedido(model))
                    .collect(Collectors.toList()));
        }

        return model;
    }

    public PedidoEntity toEntity(Pedido model) {
        if (model == null) return null;

        PedidoEntity entity = new PedidoEntity();
        entity.id = model.getId();
        entity.dtCadastro = model.getDtCadastro();
        entity.idCliente = model.getIdCliente();
        entity.idRestaurante = model.getIdRestaurante();
        entity.valorTotal = model.getValorTotal();
        entity.statusCode = model.getStatusCode();
        entity.responseCode = model.getResponseCode();

        if (model.getItens() != null) {
            model.getItens().forEach(itemModel -> {
                var itemEntity = itemMapper.toEntity(itemModel);
                entity.addItem(itemEntity);
            });
        }

        return entity;
    }
}
