package com.adjt.core.usecase;

import com.adjt.core.model.Pedido;
import com.adjt.core.port.PedidoPort;
import com.adjt.core.validator.PedidoValidator;
import jakarta.enterprise.context.ApplicationScoped;

import java.math.BigDecimal;
import java.time.LocalDateTime;

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
