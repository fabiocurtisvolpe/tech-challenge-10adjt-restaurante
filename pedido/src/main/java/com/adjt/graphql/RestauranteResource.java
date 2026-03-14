package com.adjt.graphql;

import com.adjt.core.model.CardapioInfo;
import com.adjt.core.model.RestauranteInfo;
import com.adjt.gprc.RestauranteGrpcUseCase;
import io.smallrye.mutiny.Uni;
import jakarta.annotation.security.RolesAllowed;
import org.eclipse.microprofile.graphql.Description;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Name;
import org.eclipse.microprofile.graphql.Query;

import java.util.List;

@GraphQLApi
@RolesAllowed("ROLE_CLIENTE")
public class RestauranteResource {

    private final RestauranteGrpcUseCase restauranteGrpcUseCase;

    public RestauranteResource(RestauranteGrpcUseCase restauranteGrpcUseCase) {
        this.restauranteGrpcUseCase = restauranteGrpcUseCase;
    }

    @Query("obterRestaurante")
    @Description("Busca os detalhes de um restaurante específico")
    public Uni<RestauranteInfo> obterRestaurante(@Name("id") Long id) {
        return restauranteGrpcUseCase.obterRestaurante(id);
    }

    @Query("listarRestaurantes")
    @Description("Lista todos os restaurantes com filtros opcionais")
    public Uni<List<RestauranteInfo>> listarRestaurantes(
            @Name("page") int page,
            @Name("size") int size,
            @Name("nome") String nome,
            @Name("idTipoCozinha") Long idTipoCozinha) {
        return restauranteGrpcUseCase.listarDisponiveis(page, size, nome, idTipoCozinha);
    }

    @Query("obterItemCardapio")
    @Description("Busca um item específico do cardápio")
    public Uni<CardapioInfo> obterItemCardapio(@Name("id") Long id) {
        return restauranteGrpcUseCase.obterItemCardapio(id);
    }

    @Query("listarItensCardapio")
    @Description("Lista itens do cardápio de um restaurante")
    public Uni<List<CardapioInfo>> listarItensCardapio(
            @Name("page") int page,
            @Name("size") int size,
            @Name("idRestaurante") long idRestaurante,
            @Name("disponivel") boolean disponivel) {
        return restauranteGrpcUseCase.listarItensCardapio(page, size, idRestaurante, disponivel);
    }
}
