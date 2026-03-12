package com.adjt.gprc;

import com.adjt.restaurante.RestauranteService;
import io.quarkus.grpc.GrpcClient;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class RestauranteGrpcUseCase {

    @GrpcClient("restaurante")
    RestauranteService restauranteService;
}
