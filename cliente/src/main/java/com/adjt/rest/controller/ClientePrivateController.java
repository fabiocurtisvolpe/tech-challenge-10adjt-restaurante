package com.adjt.rest.controller;

import com.adjt.core.model.Cliente;
import com.adjt.core.usecase.cliente.AtualizarClienteUseCase;
import com.adjt.core.usecase.cliente.ExcluirClienteUseCase;
import com.adjt.core.usecase.cliente.ObterPorIdClienteUseCase;
import com.adjt.rest.dto.request.ClienteRequest;
import com.adjt.rest.dto.response.ClienteResponse;
import com.adjt.rest.mapper.ClienteRestMapper;
import com.adjt.service.KeycloakSyncService;
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

    private final AtualizarClienteUseCase atualizarClienteUseCase;
    private final ObterPorIdClienteUseCase obterPorIdClienteUseCase;
    private final ExcluirClienteUseCase excluirClienteUseCase;

    private final ClienteRestMapper clienteRestMapper;

    private final KeycloakSyncService keycloakSyncService;

    public ClientePrivateController(AtualizarClienteUseCase atualizarClienteUseCase,
                                    ObterPorIdClienteUseCase obterPorIdClienteUseCase,
                                    ExcluirClienteUseCase excluirClienteUseCase,
                                    ClienteRestMapper clienteRestMapper,
                                    KeycloakSyncService keycloakSyncService) {
        this.atualizarClienteUseCase = atualizarClienteUseCase;
        this.obterPorIdClienteUseCase = obterPorIdClienteUseCase;
        this.excluirClienteUseCase = excluirClienteUseCase;
        this.clienteRestMapper = clienteRestMapper;
        this.keycloakSyncService = keycloakSyncService;
    }

    @PUT
    public Response atualizar(@Valid ClienteRequest request) {

        Cliente model = this.clienteRestMapper.toModel(request);
        Cliente resp = this.atualizarClienteUseCase.run(model);
        this.keycloakSyncService.atualizarUsuario(model);

        ClienteResponse response = this.clienteRestMapper.toResponse(resp);

        return Response.status(Response.Status.CREATED).entity(response).build();
    }

    @GET
    @Path("/{id}")
    public Response obter(@PathParam("id") @Valid Long id) {

        Cliente resp = this.obterPorIdClienteUseCase.run(id);
        ClienteResponse response = this.clienteRestMapper.toResponse(resp);

        return Response.status(Response.Status.CREATED).entity(response).build();
    }

    @DELETE
    @Path("/{id}")
    public Response excluir(@PathParam("id") @Valid Long id) {
        Cliente cliente = this.obterPorIdClienteUseCase.run(id);

        Boolean resp = this.excluirClienteUseCase.run(id);
        this.keycloakSyncService.excluirUsuario(cliente.getEmail());

        return Response.status(Response.Status.CREATED).entity(resp).build();
    }
}
