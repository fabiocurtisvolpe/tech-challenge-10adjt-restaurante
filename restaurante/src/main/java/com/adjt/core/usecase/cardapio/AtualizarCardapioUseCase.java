package com.adjt.core.usecase.cardapio;

import com.adjt.core.model.Cardapio;
import com.adjt.core.port.CardapioPort;
import com.adjt.core.validator.CardapioValidator;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class AtualizarCardapioUseCase {

    private final CardapioPort<Cardapio> cardapioPort;

    public AtualizarCardapioUseCase(CardapioPort<Cardapio> cardapioPort) {
        this.cardapioPort = cardapioPort;
    }

    public Cardapio run(Cardapio cardapio) {

        CardapioValidator.validarId(cardapio);
        CardapioValidator.camposObrigatorio(cardapio);

        return cardapioPort.atualizar(cardapio);
    }
}
