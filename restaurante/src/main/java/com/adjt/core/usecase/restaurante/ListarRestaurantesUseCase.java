package com.adjt.core.usecase.restaurante;

import com.adjt.core.model.Restaurante;
import com.adjt.core.model.RestaurantePaginado;
import com.adjt.core.port.RestaurantePort;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ListarRestaurantesUseCase {

    private final RestaurantePort<Restaurante> restaurantePort;

    public ListarRestaurantesUseCase(RestaurantePort<Restaurante> restaurantePort) {
        this.restaurantePort = restaurantePort;
    }

    public RestaurantePaginado run(int page, int size, Long idTipoCozinha, String nome) {
        return this.restaurantePort.listar(page, size, idTipoCozinha, nome);
    }
}
