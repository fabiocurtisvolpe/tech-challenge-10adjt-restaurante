package com.adjt.core.validator;

import com.adjt.core.exception.NotificacaoException;
import com.adjt.core.model.Cliente;
import com.adjt.core.util.MensagemUtil;

import java.util.regex.Pattern;

public class ClienteValidator {

    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";

    public static void validarId(Cliente cliente) {
        if (cliente.getId() == null) {
            throw new IllegalArgumentException(MensagemUtil.ID_VAZIO);
        }
    }

    public static void camposObrigatorio(Cliente cliente) {

        if (cliente == null) {
            throw new NotificacaoException(MensagemUtil.CLIENTE_NULO);
        }

        if (cliente.getNome() == null || cliente.getNome().trim().isEmpty()) {
            throw new NotificacaoException(MensagemUtil.NOME_INVALIDO);
        }

        if (cliente.getNome().length() < 3) {
            throw new NotificacaoException(MensagemUtil.NOME_INVALIDO);
        }

        if (cliente.getNome().length() > 50) {
            throw new NotificacaoException(MensagemUtil.NOME_INVALIDO);
        }

        if (cliente.getCpf() == null || cliente.getCpf().trim().isEmpty()) {
            throw new NotificacaoException(MensagemUtil.CPF_INVALIDO);
        }

        if (!validarCPF(cliente.getCpf())) {
            throw new NotificacaoException(MensagemUtil.CPF_INVALIDO);
        }

        if (cliente.getEmail() == null || cliente.getEmail().trim().isEmpty()) {
            throw new NotificacaoException(MensagemUtil.EMAIL_INVALIDO);
        }

        if (!validarEmail(cliente.getEmail())) {
            throw new NotificacaoException(MensagemUtil.EMAIL_INVALIDO);
        }

        if (cliente.getSenha() == null || cliente.getSenha().trim().isEmpty()) {
            throw new NotificacaoException(MensagemUtil.SENHA_INVALIDO);
        }

        if (cliente.getKeycloakId() == null) {
            throw new NotificacaoException(MensagemUtil.UUID_CLIENTE);
        }
    }

    private static boolean validarEmail(String email) {
        if (email == null) return false;
        return Pattern.matches(EMAIL_REGEX, email.trim());
    }

    private static boolean validarCPF(String cpf) {
        if (cpf == null) return false;

        // Remove caracteres não numéricos
        cpf = cpf.replaceAll("\\D", "");

        // CPF deve ter 11 dígitos
        if (cpf.length() != 11) return false;

        // Rejeita CPFs com todos os dígitos iguais (ex: 111.111.111-11)
        if (cpf.matches("(\\d)\\1{10}")) return false;

        try {
            // Cálculo do primeiro dígito verificador
            int soma = 0;
            for (int i = 0; i < 9; i++) {
                soma += (cpf.charAt(i) - '0') * (10 - i);
            }
            int resto = 11 - (soma % 11);
            int digito1 = (resto == 10 || resto == 11) ? 0 : resto;

            // Cálculo do segundo dígito verificador
            soma = 0;
            for (int i = 0; i < 10; i++) {
                soma += (cpf.charAt(i) - '0') * (11 - i);
            }
            resto = 11 - (soma % 11);
            int digito2 = (resto == 10 || resto == 11) ? 0 : resto;

            // Verifica se os dígitos calculados batem com os informados
            return digito1 == (cpf.charAt(9) - '0') &&
                    digito2 == (cpf.charAt(10) - '0');

        } catch (NumberFormatException e) {
            return false;
        }
    }
}
