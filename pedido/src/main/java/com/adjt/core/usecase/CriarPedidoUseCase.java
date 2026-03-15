package com.adjt.core.usecase;

import com.adjt.core.model.Pedido;
import com.adjt.core.port.PedidoPort;
import com.adjt.core.validator.PedidoValidator;
import jakarta.enterprise.context.ApplicationScoped;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@ApplicationScoped
public class CriarPedidoUseCase {

    private final PedidoPort<Pedido> pedidoPort;

    public CriarPedidoUseCase(PedidoPort<Pedido> pedidoPort) {
        this.pedidoPort = pedidoPort;
    }

    public Pedido run(Pedido pedido) {
        PedidoValidator.validar(pedido);

        pedido.setDtCadastro(LocalDateTime.now());
        BigDecimal total = pedido.getItens().stream()
                .map(item -> {
                    BigDecimal qtd = BigDecimal.valueOf(item.getQtd());
                    return item.getValor().multiply(qtd);
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        pedido.setValorTotal(total);

        return pedidoPort.criar(pedido);
    }
}
