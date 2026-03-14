package com.adjt.core.usecase;

import com.adjt.core.model.Pedido;
import com.adjt.core.port.PedidoPort;
import com.adjt.core.validator.PedidoValidator;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CancelarPedidoUseCase {

    private final PedidoPort<Pedido> pedidoPort;

    public CancelarPedidoUseCase(PedidoPort<Pedido> pedidoPort) {
        this.pedidoPort = pedidoPort;
    }

    public boolean run(Long id) {
        PedidoValidator.validarId(id);
        return pedidoPort.cancelar(id);
    }
}
