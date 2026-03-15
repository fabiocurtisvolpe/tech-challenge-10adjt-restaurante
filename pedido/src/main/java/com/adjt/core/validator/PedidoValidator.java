package com.adjt.core.validator;

import com.adjt.core.model.ItemPedido;
import com.adjt.core.model.Pedido;
import com.adjt.core.util.MensagemUtil;

import java.math.BigDecimal;
import java.util.List;

public class PedidoValidator {

    public static void validar(Pedido pedido) {

        if (pedido.getIdCliente() == null) {
            throw new IllegalArgumentException(MensagemUtil.CLIENTE_OBRIGATORIO);
        }

        if (pedido.getIdRestaurante() == null) {
            throw new IllegalArgumentException(MensagemUtil.RESTAURANTE_OBRIGATORIO);
        }

        if (pedido.getItens() == null || pedido.getItens().isEmpty()) {
            throw new IllegalArgumentException(MensagemUtil.PEDIDO_DEVE_TER_ITENS);
        }

        validarItens(pedido.getItens());
    }

    private static void validarItens(List<ItemPedido> itens) {
        for (ItemPedido item : itens) {
            if (item.getIdCardapio() == null) {
                throw new IllegalArgumentException(MensagemUtil.ITEM_CARDAPIO_OBRIGATORIO);
            }

            if (item.getQtd() == null || item.getQtd() < 1) {
                throw new IllegalArgumentException(MensagemUtil.ITEM_QUANTIDADE_MINIMA);
            }

            if (item.getValor() == null || item.getValor().compareTo(BigDecimal.ZERO) <= 0) {
                throw new IllegalArgumentException(MensagemUtil.ITEM_VALOR_POSITIVO);
            }
        }
    }

    public static void validarId(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException(MensagemUtil.ID_OBRIGATORIO);
        }
    }
}
