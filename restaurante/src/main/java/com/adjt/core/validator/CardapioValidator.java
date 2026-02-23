package com.adjt.core.validator;

import com.adjt.core.model.Cardapio;
import com.adjt.core.util.MensagemUtil;

public class CardapioValidator {

    public static void validarId(Cardapio cardapio) {
        if (cardapio.getId() == null) {
            throw new IllegalArgumentException(MensagemUtil.ID_VAZIO);
        }
    }

    public static void camposObrigatorio(Cardapio cardapio) {

        if (cardapio == null) {
            throw new IllegalArgumentException(MensagemUtil.CARDAPIO_NULO);
        }

        if (cardapio.getNome() == null || cardapio.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException(MensagemUtil.NOME_CARDAPIO_OBRIGATORIO);
        }

        if (cardapio.getRestaurante() == null) {
            throw new IllegalArgumentException(MensagemUtil.RESTAURANTE_OBRIGATORIO);
        }
    }
}
