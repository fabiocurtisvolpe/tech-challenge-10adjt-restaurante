package com.adjt.core.usecase.cardapio;

import com.adjt.core.model.Cardapio;
import com.adjt.core.port.CardapioPort;
import com.adjt.core.validator.CardapioValidator;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CriarCardapioUseCase {

    private final CardapioPort<Cardapio> cardapioPort;

    public CriarCardapioUseCase(CardapioPort<Cardapio> cardapioPort) {
        this.cardapioPort = cardapioPort;
    }

    public Cardapio run(Cardapio cardapio) {
        CardapioValidator.camposObrigatorio(cardapio);
        return cardapioPort.criar(cardapio);
    }
}
