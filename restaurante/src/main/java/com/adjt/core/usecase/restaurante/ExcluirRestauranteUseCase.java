package com.adjt.core.usecase.restaurante;

import com.adjt.core.model.Restaurante;
import com.adjt.core.port.RestaurantePort;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ExcluirRestauranteUseCase {

    private final RestaurantePort<Restaurante> restaurantePort;

    public ExcluirRestauranteUseCase(RestaurantePort<Restaurante> restaurantePort) {
        this.restaurantePort = restaurantePort;
    }

    public boolean run(Long id) {
        return restaurantePort.excluir(id);
    }
}
