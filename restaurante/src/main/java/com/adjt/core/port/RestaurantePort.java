package com.adjt.core.port;

import com.adjt.core.model.RestaurantePaginado;

public interface RestaurantePort<Restaurante> {

    Restaurante criar(Restaurante model);
    Restaurante atualizar(Restaurante model);
    Boolean excluir(Long id);
    Restaurante obterPorId(Long id);
    RestaurantePaginado listar(int page, int size, Long idTipoCozinha, String nome);
}
