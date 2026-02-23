package com.adjt.core.port;

public interface CardapioPort<Cardapio> {

    Cardapio criar(Cardapio model);
    Cardapio atualizar(Cardapio model);
    Boolean excluir(Long id);
    Cardapio obterPorId(Long id);
}
