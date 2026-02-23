package com.adjt.core.usecase.cardapio;

import com.adjt.core.model.Cardapio;
import com.adjt.core.port.CardapioPort;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ExcluirCardapioUseCase {

    private final CardapioPort<Cardapio> cardapioPort;

    public ExcluirCardapioUseCase(CardapioPort<Cardapio> cardapioPort) {
        this.cardapioPort = cardapioPort;
    }

    public boolean run(Long id) {
        return cardapioPort.excluir(id);
    }
}
