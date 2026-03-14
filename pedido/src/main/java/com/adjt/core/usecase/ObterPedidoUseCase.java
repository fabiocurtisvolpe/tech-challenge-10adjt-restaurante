package com.adjt.core.usecase;

import com.adjt.core.model.Pedido;
import com.adjt.core.port.PedidoPort;
import com.adjt.core.validator.PedidoValidator;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ObterPedidoUseCase {

    private final PedidoPort<Pedido> pedidoPort;

    public ObterPedidoUseCase(PedidoPort<Pedido> pedidoPort) {
        this.pedidoPort = pedidoPort;
    }

    public Pedido run(Long id) {
        PedidoValidator.validarId(id);
        return pedidoPort.obterPorId(id);
    }
}
