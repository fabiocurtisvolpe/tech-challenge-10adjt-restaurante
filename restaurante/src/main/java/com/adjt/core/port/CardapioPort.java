package com.adjt.core.port;

import java.util.List;

public interface CardapioPort<Cardapio> {

    Cardapio criar(Cardapio model);
    Cardapio atualizar(Cardapio model);
    Boolean excluir(Long id);
    Cardapio obterPorId(Long id);
    List<Cardapio> listarPorRestaurante(Long idRestaurante, int page, int size, Boolean disponivel);
}
