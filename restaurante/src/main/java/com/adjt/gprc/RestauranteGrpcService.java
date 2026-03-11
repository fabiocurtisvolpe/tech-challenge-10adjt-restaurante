package com.adjt.gprc;

import com.adjt.core.exception.NotificacaoException;
import com.adjt.data.repository.adapter.CardapioRepositoryAdapter;
import com.adjt.data.repository.adapter.RestauranteRepositoryAdapter;
import com.adjt.gprc.mapper.RestauranteGrpcMapper;
import com.adjt.restaurante.*;
import io.grpc.Status;
import io.quarkus.grpc.GrpcService;
import io.smallrye.common.annotation.RunOnVirtualThread;
import io.smallrye.mutiny.Uni;

@GrpcService
public class RestauranteGrpcService extends MutinyRestauranteServiceGrpc.RestauranteServiceImplBase {

    private final RestauranteRepositoryAdapter restauranteAdapter;
    private final CardapioRepositoryAdapter cardapioAdapter;

    public RestauranteGrpcService(RestauranteRepositoryAdapter restauranteAdapter,
                                  CardapioRepositoryAdapter cardapioAdapter) {
        this.restauranteAdapter = restauranteAdapter;
        this.cardapioAdapter = cardapioAdapter;
    }

    @Override
    @RunOnVirtualThread
    public Uni<RestauranteResponse> obterRestaurante(ObterRestauranteRequest request) {
        return Uni.createFrom()
                .item(() -> restauranteAdapter.obterPorId(request.getId()))
                .onItem().ifNull().failWith(() ->
                        Status.NOT_FOUND
                                .withDescription("Restaurante não encontrado para id: " + request.getId())
                                .asRuntimeException()
                )
                .onFailure(NotificacaoException.class).transform(e ->
                        Status.NOT_FOUND.withDescription(e.getMessage()).asRuntimeException()
                )
                .onItem().ifNotNull().transform(RestauranteGrpcMapper::toProtoResponse);
    }

    @Override
    @RunOnVirtualThread
    public Uni<ListarRestaurantesResponse> listarRestaurantes(ListarRestaurantesRequest request) {
        return Uni.createFrom()
                .item(() -> restauranteAdapter.listar(
                        request.getPage(),
                        request.getSize(),
                        request.hasIdTipoCozinha() ? request.getIdTipoCozinha() : null,
                        request.hasNome() ? request.getNome() : null
                ))
                .onItem().ifNotNull().transform(RestauranteGrpcMapper::toProtoListResponse)
                .onFailure().transform(e ->
                        Status.INTERNAL.withDescription(e.getMessage()).asRuntimeException()
                );
    }

    @Override
    @RunOnVirtualThread
    public Uni<ItemCardapioResponse> obterItemCardapio(ObterItemCardapioRequest request) {
        return Uni.createFrom()
                .item(() -> cardapioAdapter.obterPorId(request.getId()))
                .onItem().ifNull().failWith(() ->
                        Status.NOT_FOUND
                                .withDescription("Item não encontrado para id: " + request.getId())
                                .asRuntimeException()
                )
                .onItem().ifNotNull().transform(RestauranteGrpcMapper::toProtoItemResponse);
    }

    @Override
    @RunOnVirtualThread
    public Uni<ListarItensCardapioResponse> listarItensCardapio(ListarItensCardapioRequest request) {
        return Uni.createFrom()
                .item(() -> cardapioAdapter.listarPorRestaurante(
                        request.getIdRestaurante(),
                        request.getPage(),
                        request.getSize(),
                        request.hasDisponivel() ? request.getDisponivel() : null
                ))
                .onItem().ifNotNull().transform(RestauranteGrpcMapper::toProtoListCardapioResponse)
                .onFailure().transform(e ->
                        Status.INTERNAL.withDescription(e.getMessage()).asRuntimeException()
                );
    }
}
