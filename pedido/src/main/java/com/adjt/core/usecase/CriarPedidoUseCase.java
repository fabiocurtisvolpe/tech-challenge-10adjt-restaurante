package com.adjt.core.usecase;

import com.adjt.core.model.Pedido;
import com.adjt.core.port.PedidoPort;
import com.adjt.core.validator.PedidoValidator;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CriarPedidoUseCase {

    private final PedidoPort<Pedido> pedidoPort;

    public CriarPedidoUseCase(PedidoPort<Pedido> pedidoPort) {
        this.pedidoPort = pedidoPort;
    }

    public Pedido run(Pedido pedido) {
        PedidoValidator.validar(pedido);
        return pedidoPort.criar(pedido);
    }
}
