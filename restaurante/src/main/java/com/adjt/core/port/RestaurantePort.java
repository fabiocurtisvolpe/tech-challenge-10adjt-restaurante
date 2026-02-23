package com.adjt.core.port;

public interface RestaurantePort<Restaurante> {

    Restaurante criar(Restaurante model);
    Restaurante atualizar(Restaurante model);
    Boolean excluir(Long id);
    Restaurante obterPorId(Long id);
}
