package com.adjt.rest.mapper;

import com.adjt.core.model.Restaurante;
import com.adjt.rest.dto.request.RestauranteRequest;
import com.adjt.rest.dto.response.RestauranteResponse;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class RestauranteRestMapper {

    public Restaurante toModel(RestauranteRequest request) {
        if (request == null) {
            return null;
        }

        Restaurante restaurante = new Restaurante();
        restaurante.setId(request.id);
        restaurante.setNome(request.nome);
        restaurante.setDescricao(request.descricao);

        return restaurante;

    }

    public RestauranteResponse toResponse(Restaurante model) {
        return null;
    }
}
