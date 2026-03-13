package com.adjt.core.port;

import com.adjt.core.model.CardapioPaginado;

public interface CardapioPort<Cardapio> {

    Cardapio criar(Cardapio model);
    Cardapio atualizar(Cardapio model);
    Boolean excluir(Long id);
    Cardapio obterPorId(Long id);
    CardapioPaginado listarPorRestaurante(Long idRestaurante, int page, int size, Boolean disponivel);
}
