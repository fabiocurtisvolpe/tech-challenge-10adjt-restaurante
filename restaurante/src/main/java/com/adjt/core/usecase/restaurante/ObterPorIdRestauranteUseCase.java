package com.adjt.core.usecase.restaurante;

import com.adjt.core.model.Restaurante;
import com.adjt.core.port.RestaurantePort;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ObterPorIdRestauranteUseCase {

    private final RestaurantePort<Restaurante> restaurantePort;

    public ObterPorIdRestauranteUseCase(RestaurantePort<Restaurante> restaurantePort) {
        this.restaurantePort = restaurantePort;
    }

    public Restaurante run(Long id) {
        return restaurantePort.obterPorId(id);
    }
}
