package com.adjt.core.validator;

import com.adjt.core.model.Restaurante;
import com.adjt.core.util.MensagemUtil;
import com.adjt.core.util.TamanhoUtil;

public class RestauranteValidator {

    public static void validarId(Restaurante restaurante) {
        if (restaurante.getId() == null) {
            throw new IllegalArgumentException(MensagemUtil.ID_VAZIO);
        }
    }

    public static void camposObrigatorio(Restaurante restaurante) {

        // 1. Validação de existência do objeto
        if (restaurante == null) {
            throw new IllegalArgumentException(MensagemUtil.RESTAURANTE_NULO);
        }

        // 2. Validação do Nome (Obrigatoriedade e Comprimento)
        if (restaurante.getNome() == null || restaurante.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException(MensagemUtil.NOME_RESTAURANTE_OBRIGATORIO);
        }

        if (restaurante.getNome().length() < TamanhoUtil.NOME_MINIMO_LENGTH) {
            throw new IllegalArgumentException(MensagemUtil.NOME_MINIMO_CARACTERES);
        }

        if (restaurante.getNome().length() > TamanhoUtil.NOME_MAXIMO_LENGTH) {
            throw new IllegalArgumentException(MensagemUtil.NOME_MAXIMO_CARACTERES);
        }

        // 3. Validação do Horário (Delegada ao validador especializado)
        if (restaurante.getHorarioFuncionamento() == null || restaurante.getHorarioFuncionamento().trim().isEmpty()) {
            throw new IllegalArgumentException(MensagemUtil.HORARIO_FUNCIONAMENTO_OBRIGATORIO);
        }

        // 4. Validação do Tipo de Cozinha
        if (restaurante.getTipoCozinha() == null) {
            throw new IllegalArgumentException(MensagemUtil.TIPO_COZINHA_OBRIGATORIO);
        }

        // 5. Regras de Permissão e Propriedade (Autorização)
        if (restaurante.getDono() == null) {
            throw new IllegalArgumentException(MensagemUtil.DONO_RESTAURANTE_OBRIGATORIO);
        }

        // 6. Validação de Descrição (Opcional, mas com limite de tamanho)
        if (restaurante.getDescricao() != null && restaurante.getDescricao().length() > TamanhoUtil.DESCRICAO_MAXIMA_LENGTH) {
            throw new IllegalArgumentException(MensagemUtil.DESCRICAO_MAXIMO_CARACTERES);
        }

        // 7. Validação lógica da estrutura do JSON de Horários
        HorarioValidator.validar(restaurante.getHorarioFuncionamento());
    }
}
