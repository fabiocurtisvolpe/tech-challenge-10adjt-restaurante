package com.adjt.data.repository.adapter;

import com.adjt.core.model.CardapioInfo;
import com.adjt.core.port.RestauranteExternalPort;
import com.adjt.gprc.RestauranteGrpcUseCase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class RestauranteExternalAdapter implements RestauranteExternalPort {

    @Inject
    RestauranteGrpcUseCase grpcUseCase;

    @Override
    public CardapioInfo obterItemCardapio(Long id) {
        return grpcUseCase.obterItemCardapio(id).await().indefinitely();
    }
}
