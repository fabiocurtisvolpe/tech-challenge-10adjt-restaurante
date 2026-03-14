package com.adjt.core.port;

public interface PedidoPort<Pedido> {

    Pedido criar(Pedido model);
    boolean cancelar(Long id);
    Pedido obterPorId(Long id);
}
