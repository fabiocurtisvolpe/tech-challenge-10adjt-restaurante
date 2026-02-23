package com.adjt.core.usecase.restaurante;

import com.adjt.core.model.Restaurante;
import com.adjt.core.port.RestaurantePort;
import com.adjt.core.validator.RestauranteValidator;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CriarRestauranteUseCase {

    private final RestaurantePort<Restaurante> restaurantePort;

    public CriarRestauranteUseCase(RestaurantePort<Restaurante> restaurantePort) {
        this.restaurantePort = restaurantePort;
    }

    public Restaurante run(Restaurante restaurante) {
        RestauranteValidator.camposObrigatorio(restaurante);
        return restaurantePort.criar(restaurante);
    }
}
