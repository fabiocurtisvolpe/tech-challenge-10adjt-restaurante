package com.adjt.core.port;

public interface PedidoPort<Pedido> {

    Pedido criar(Pedido model);
    Pedido atualizar(Long id, Integer statusCode);
    boolean cancelar(Long id);
    Pedido obterPorId(Long id);
}
