package com.adjt.core.usecase.cardapio;

import com.adjt.core.model.Cardapio;
import com.adjt.core.port.CardapioPort;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ObterPorIdCardapioUseCase {

    private final CardapioPort<Cardapio> cardapioPort;

    public ObterPorIdCardapioUseCase(CardapioPort<Cardapio> cardapioPort) {
        this.cardapioPort = cardapioPort;
    }

    public Cardapio run(Long id) {
        return cardapioPort.obterPorId(id);
    }
}
