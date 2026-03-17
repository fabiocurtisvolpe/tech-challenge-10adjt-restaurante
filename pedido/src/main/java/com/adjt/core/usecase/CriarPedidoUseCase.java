package com.adjt.core.usecase;

import com.adjt.base.core.exception.NotificacaoException;
import com.adjt.base.core.model.StatusPagamento;
import com.adjt.core.model.CardapioInfo;
import com.adjt.core.model.ItemPedido;
import com.adjt.core.model.Pedido;
import com.adjt.core.port.PedidoPort;
import com.adjt.core.port.RestauranteExternalPort;
import com.adjt.core.util.MensagemUtil;
import com.adjt.core.validator.PedidoValidator;
import jakarta.enterprise.context.ApplicationScoped;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@ApplicationScoped
public class CriarPedidoUseCase {

    private final PedidoPort<Pedido> pedidoPort;
    private final RestauranteExternalPort restaurantePort;

    public CriarPedidoUseCase(PedidoPort<Pedido> pedidoPort,
                              RestauranteExternalPort restaurantePort) {
        this.pedidoPort = pedidoPort;
        this.restaurantePort = restaurantePort;
    }

    public Pedido run(Pedido pedido) {
        PedidoValidator.validar(pedido);
        validarItensDoRestaurante(pedido);

        pedido.setDtCadastro(LocalDateTime.now());
        calcularTotal(pedido);
        pedido.setStatusCode(StatusPagamento.PEDIDO_RECEBIDO.getHttpStatus());

        return pedidoPort.criar(pedido);
    }

    private void validarItensDoRestaurante(Pedido pedido) {
        for (ItemPedido item : pedido.getItens()) {

            CardapioInfo info = restaurantePort.obterItemCardapio(item.getIdCardapio());

            if (info == null) {
                throw new NotificacaoException("Item do cardápio não encontrado: " + item.getIdCardapio());
            }

            if (!info.getIdRestaurante().equals(pedido.getIdRestaurante())) {
                throw new NotificacaoException(
                        MensagemUtil.ITEM_NAO_PERTENCE_AO_RESTAURANTE.formatted(item.getIdCardapio())
                );
            }

            item.setValor(info.getPreco());
        }
    }

    private void calcularTotal(Pedido pedido) {
        BigDecimal total = pedido.getItens().stream()
                .map(item -> item.getValor().multiply(BigDecimal.valueOf(item.getQtd())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        pedido.setValorTotal(total);
    }
}
