package com.adjt.rest.controller;

import com.adjt.core.model.Usuario;
import com.adjt.core.usecase.usuario.AtualizarUsuarioUseCase;
import com.adjt.core.usecase.usuario.ExcluirUsuarioUseCase;
import com.adjt.core.usecase.usuario.ObterPorIdUsuarioUseCase;
import com.adjt.rest.dto.request.ClienteRequest;
import com.adjt.rest.dto.response.ClienteResponse;
import com.adjt.rest.mapper.ClienteRestMapper;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/cliente-private")
@RolesAllowed("ROLE_CLIENTE")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ClientePrivateController {

    private final AtualizarUsuarioUseCase atualizarUsuarioUseCase;
    private final ObterPorIdUsuarioUseCase obterPorIdUsuarioUseCase;
    private final ExcluirUsuarioUseCase excluirUsuarioUseCase;

    private final ClienteRestMapper clienteRestMapper;

    public ClientePrivateController(AtualizarUsuarioUseCase atualizarUsuarioUseCase,
                                    ObterPorIdUsuarioUseCase obterPorIdUsuarioUseCase,
                                    ExcluirUsuarioUseCase excluirUsuarioUseCase,
                                    ClienteRestMapper clienteRestMapper) {
        this.atualizarUsuarioUseCase = atualizarUsuarioUseCase;
        this.obterPorIdUsuarioUseCase = obterPorIdUsuarioUseCase;
        this.excluirUsuarioUseCase = excluirUsuarioUseCase;
        this.clienteRestMapper = clienteRestMapper;
    }

    @PUT
    public Response atualizar(@Valid ClienteRequest request) {

        Usuario model = this.clienteRestMapper.toModel(request);
        Usuario resp = this.atualizarUsuarioUseCase.run(model);
        ClienteResponse response = this.clienteRestMapper.toResponse(resp);

        return Response.status(Response.Status.CREATED).entity(response).build();
    }

    @GET
    @Path("/{id}")
    public Response obter(@PathParam("id") @Valid Long id) {

        Usuario resp = this.obterPorIdUsuarioUseCase.run(id);
        ClienteResponse response = this.clienteRestMapper.toResponse(resp);

        return Response.status(Response.Status.CREATED).entity(response).build();
    }

    @DELETE
    @Path("/{id}")
    public Response excluir(@PathParam("id") @Valid Long id) {
        Boolean resp = this.excluirUsuarioUseCase.run(id);
        return Response.status(Response.Status.CREATED).entity(resp).build();
    }
}
