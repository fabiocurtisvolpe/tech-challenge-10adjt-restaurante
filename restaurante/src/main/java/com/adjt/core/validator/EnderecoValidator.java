package com.adjt.core.validator;

import com.adjt.base.core.exception.NotificacaoException;
import com.adjt.core.model.Endereco;
import com.adjt.core.util.MensagemUtil;

import java.util.List;
import java.util.regex.Pattern;

public class EnderecoValidator {

    private static final String CEP_REGEX = "^[0-9]{5}-?[0-9]{3}$";

    public static void validarId(List<Endereco> enderecos) {

        for (Endereco endereco : enderecos) {
            if (endereco.getId() == null) {
                throw new IllegalArgumentException(MensagemUtil.ID_VAZIO);
            }
        }
    }

    public static void camposObrigatorio(List<Endereco> enderecos) {

        if (enderecos.isEmpty()) {
            throw new NotificacaoException(MensagemUtil.ENDERECO_OBRIGATORIO);
        }

        for (Endereco endereco : enderecos) {
            validarEndereco(endereco);
        }
    }

    private static boolean isStringVazia(String valor) {
        return valor == null || valor.trim().isEmpty();
    }

    private static void validarEndereco(Endereco endereco) {

        if (isStringVazia(endereco.getRua())) {
            throw new NotificacaoException(MensagemUtil.RUA_INVALIDO);
        }

        if (isStringVazia(endereco.getBairro())) {
            throw new NotificacaoException(MensagemUtil.BAIRRO_INVALIDO);
        }

        if (isStringVazia(endereco.getCidade())) {
            throw new NotificacaoException(MensagemUtil.MUNICIPIO_INVALIDO);
        }

        if (isStringVazia(endereco.getUf())) {
            throw new NotificacaoException(MensagemUtil.UF_OBRIGATORIO);
        }

        if (endereco.getUf().length() != 2) {
            throw new NotificacaoException(MensagemUtil.UF_INVALIDO);
        }

        if (!validarCEP(endereco.getCep())) {
            throw new NotificacaoException(MensagemUtil.CEP_INVALIDO);
        }
    }

    private static boolean validarCEP(String cep) {
        if (cep == null) return false;
        return Pattern.matches(CEP_REGEX, cep.trim());
    }
}
