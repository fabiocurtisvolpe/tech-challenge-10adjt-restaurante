package com.adjt.core.port;

import java.util.List;

public interface RestaurantePort<Restaurante> {

    Restaurante criar(Restaurante model);
    Restaurante atualizar(Restaurante model);
    Boolean excluir(Long id);
    Restaurante obterPorId(Long id);
    List<Restaurante> listar(int page, int size, Long idTipoCozinha, String nome);
}
