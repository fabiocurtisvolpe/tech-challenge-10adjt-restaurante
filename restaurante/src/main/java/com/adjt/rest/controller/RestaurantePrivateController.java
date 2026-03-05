package com.adjt.rest.controller;

import com.adjt.core.model.Restaurante;
import com.adjt.core.model.TipoCozinha;
import com.adjt.core.model.Usuario;
import com.adjt.core.usecase.restaurante.AtualizarRestauranteUseCase;
import com.adjt.core.usecase.restaurante.CriarRestauranteUseCase;
import com.adjt.core.usecase.restaurante.ExcluirRestauranteUseCase;
import com.adjt.core.usecase.restaurante.ObterPorIdRestauranteUseCase;
import com.adjt.core.usecase.tipocozinha.ObterPorIdTipoCozinhaUseCase;
import com.adjt.core.usecase.usuario.ObterPorIdUsuarioUseCase;
import com.adjt.rest.dto.request.RestauranteRequest;
import com.adjt.rest.dto.response.RestauranteResponse;
import com.adjt.rest.mapper.RestauranteRestMapper;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/restaurante-private")
@RolesAllowed("ROLE_DONO")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RestaurantePrivateController {

    private final CriarRestauranteUseCase criarRestauranteUseCase;
    private final AtualizarRestauranteUseCase atualizarRestauranteUseCase;
    private final ExcluirRestauranteUseCase excluirRestauranteUseCase;
    private final ObterPorIdRestauranteUseCase obterPorIdRestauranteUseCase;

    private final ObterPorIdUsuarioUseCase obterPorIdUsuarioUseCase;
    private final ObterPorIdTipoCozinhaUseCase obterPorIdTipoCozinhaUseCase;

    private final RestauranteRestMapper restauranteRestMapper;

    public RestaurantePrivateController(CriarRestauranteUseCase criarRestauranteUseCase,
                                        AtualizarRestauranteUseCase atualizarRestauranteUseCase,
                                        ExcluirRestauranteUseCase excluirRestauranteUseCase,
                                        ObterPorIdRestauranteUseCase obterPorIdRestauranteUseCase,
                                        ObterPorIdUsuarioUseCase obterPorIdUsuarioUseCase,
                                        ObterPorIdTipoCozinhaUseCase obterPorIdTipoCozinhaUseCase,
                                        RestauranteRestMapper restauranteRestMapper) {

        this.criarRestauranteUseCase = criarRestauranteUseCase;
        this.atualizarRestauranteUseCase = atualizarRestauranteUseCase;
        this.excluirRestauranteUseCase = excluirRestauranteUseCase;
        this.obterPorIdRestauranteUseCase = obterPorIdRestauranteUseCase;

        this.obterPorIdUsuarioUseCase = obterPorIdUsuarioUseCase;
        this.obterPorIdTipoCozinhaUseCase = obterPorIdTipoCozinhaUseCase;

        this.restauranteRestMapper = restauranteRestMapper;
    }

    @POST
    public Response criar(@Valid RestauranteRequest request) {

        Usuario usuario = this.obterPorIdUsuarioUseCase.run(request.idDono);
        TipoCozinha tipoCozinha = this.obterPorIdTipoCozinhaUseCase.run(request.idTipoCozinha);

        Restaurante model = this.restauranteRestMapper.toModel(request);
        model.setDono(usuario);
        model.setTipoCozinha(tipoCozinha);

        Restaurante resp = this.criarRestauranteUseCase.run(model);
        RestauranteResponse response = this.restauranteRestMapper.toResponse(resp);

        return Response.status(Response.Status.CREATED).entity(response).build();
    }

    @PUT
    public Response atualizar(@Valid RestauranteRequest request) {

        Usuario usuario = this.obterPorIdUsuarioUseCase.run(request.idDono);
        TipoCozinha tipoCozinha = this.obterPorIdTipoCozinhaUseCase.run(request.idTipoCozinha);

        Restaurante model = this.restauranteRestMapper.toModel(request);
        model.setDono(usuario);
        model.setTipoCozinha(tipoCozinha);

        Restaurante resp = this.atualizarRestauranteUseCase.run(model);
        RestauranteResponse response = this.restauranteRestMapper.toResponse(resp);

        return Response.status(Response.Status.CREATED).entity(response).build();
    }

    @GET
    @Path("/{id}")
    public Response obter(@PathParam("id") @Valid Long id) {

        Restaurante resp = this.obterPorIdRestauranteUseCase.run(id);
        RestauranteResponse response = this.restauranteRestMapper.toResponse(resp);

        return Response.ok(response).build();
    }

    @DELETE
    @Path("/{id}")
    public Response excluir(@PathParam("id") @Valid Long id) {
        boolean resp = this.excluirRestauranteUseCase.run(id);

        if (!resp) {
            return Response.status(Response.Status.NOT_FOUND).build(); // 404
        }
        return Response.noContent().build(); // 204
    }
}
