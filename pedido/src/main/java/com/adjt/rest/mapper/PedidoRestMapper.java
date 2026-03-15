package com.adjt.rest.mapper;

import com.adjt.core.model.ItemPedido;
import com.adjt.core.model.Pedido;
import com.adjt.rest.dto.request.ItemPedidoRequest;
import com.adjt.rest.dto.request.PedidoRequest;
import com.adjt.rest.dto.response.PedidoResponse;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class PedidoRestMapper {

    public Pedido toModel(PedidoRequest request) {
        if (request == null) return null;

        Pedido pedido = new Pedido();
        pedido.setIdCliente(request.idCliente);
        pedido.setIdRestaurante(request.idRestaurante);

        if (request.itens != null) {
            List<ItemPedido> itensModel = request.itens.stream()
                    .map(this::toItemModel)
                    .collect(Collectors.toList());
            pedido.setItens(itensModel);

            itensModel.forEach(item -> item.setPedido(pedido));
        }

        return pedido;
    }

    /**
     * Converte Pedido (Modelo de Domínio) para PedidoResponse (Saída da API)
     */
    public PedidoResponse toResponse(Pedido model) {
        if (model == null) return null;

        PedidoResponse response = new PedidoResponse();
        response.id = model.getId();
        response.dtCadastro = model.getDtCadastro();
        response.idCliente = model.getIdCliente();
        response.idRestaurante = model.getIdRestaurante();
        response.valorTotal = model.getValorTotal();

        response.pgtoEfetuado = model.getStatusCode() != null && model.getStatusCode() == 201;

        if (model.getItens() != null) {
            response.itens = new ArrayList<>(model.getItens());
        }

        return response;
    }

    private ItemPedido toItemModel(ItemPedidoRequest itemRequest) {
        if (itemRequest == null) return null;

        ItemPedido item = new ItemPedido();
        item.setIdCardapio(itemRequest.idCardapio);
        item.setQtd(itemRequest.qtd);
        item.setValor(itemRequest.valor);
        return item;
    }

}
