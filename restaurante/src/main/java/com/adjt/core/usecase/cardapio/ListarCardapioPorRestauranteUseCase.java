package com.adjt.core.usecase.cardapio;

import com.adjt.core.model.Cardapio;
import com.adjt.core.model.CardapioPaginado;
import com.adjt.core.port.CardapioPort;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ListarCardapioPorRestauranteUseCase {

    private final CardapioPort<Cardapio> cardapioPort;

    public ListarCardapioPorRestauranteUseCase(CardapioPort<Cardapio> cardapioPort) {
        this.cardapioPort = cardapioPort;
    }

    public CardapioPaginado run(Long idRestaurante, int page, int size, Boolean disponivel) {
        return this.cardapioPort.listarPorRestaurante(idRestaurante, page, size, disponivel);
    }
}
