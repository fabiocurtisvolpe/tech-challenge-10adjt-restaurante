package com.adjt.core.usecase;

import com.adjt.core.model.Pedido;
import com.adjt.core.port.PedidoPort;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class AtualizarPedidoUseCase {

    private final PedidoPort<Pedido> pedidoPort;

    public AtualizarPedidoUseCase(PedidoPort<Pedido> pedidoPort) {
        this.pedidoPort = pedidoPort;
    }

    public Pedido run(Long id, Integer statusCode) {
        return pedidoPort.atualizar(id, statusCode);
    }
}
