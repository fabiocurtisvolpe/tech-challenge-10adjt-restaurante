package com.adjt.gprc;

import com.adjt.core.model.CardapioInfo;
import com.adjt.core.model.RestauranteInfo;
import com.adjt.gprc.mapper.RestaurantePedidoMapper;
import com.adjt.restaurante.*;
import io.quarkus.grpc.GrpcClient;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class RestauranteGrpcUseCase {

    @GrpcClient("restaurante")
    RestauranteService restauranteService;

    public Uni<RestauranteInfo> obterRestaurante(Long id) {
        ObterRestauranteRequest request = ObterRestauranteRequest.newBuilder()
                .setId(id)
                .build();

        return restauranteService.obterRestaurante(request)
                .map(RestaurantePedidoMapper::toRestauranteInfo);
    }

    public Uni<List<RestauranteInfo>> listarDisponiveis(int page, int size, String nome, Long idTipoCozinha) {

        ListarRestaurantesRequest request = ListarRestaurantesRequest.newBuilder()
                .setPage(page)
                .setSize(size)
                .setNome(nome != null ? nome : "")
                .setIdTipoCozinha(idTipoCozinha)
                .build();

        return restauranteService.listarRestaurantes(request)
                .map(response -> response.getRestaurantesList().stream()
                        .map(r -> new RestauranteInfo(r.getId(), r.getNome(), r.getDescricao()))
                        .collect(Collectors.toList())
                );
    }

    public Uni<CardapioInfo> obterItemCardapio(Long id) {
        ObterItemCardapioRequest request = ObterItemCardapioRequest.newBuilder()
                .setId(id)
                .build();

        return restauranteService.obterItemCardapio(request)
                .map(RestaurantePedidoMapper::toCardapioInfo);
    }

    public Uni<List<CardapioInfo>> listarItensCardapio(int page, int size, long idRestaurante, boolean disponivel) {
        ListarItensCardapioRequest request = ListarItensCardapioRequest.newBuilder()
                .setPage(page)
                .setSize(size)
                .setDisponivel(disponivel)
                .setIdRestaurante(idRestaurante)
                .build();

        return restauranteService.listarItensCardapio(request)
                .map(response -> response.getItensList().stream()
                        .map(r -> new CardapioInfo(r.getId(), r.getNome(), r.getDescricao(), r.getPreco(), r.getFoto()))
                        .collect(Collectors.toList())
                );
    }
}
