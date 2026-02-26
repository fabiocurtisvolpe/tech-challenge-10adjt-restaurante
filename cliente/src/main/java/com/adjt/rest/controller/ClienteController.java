package com.adjt.rest.controller;

import com.adjt.core.model.Cliente;
import com.adjt.core.usecase.cliente.CriarClienteUseCase;
import com.adjt.rest.dto.request.ClienteRequest;
import com.adjt.rest.dto.response.ClienteResponse;
import com.adjt.rest.mapper.ClienteRestMapper;
import com.adjt.service.KeycloakSyncService;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/cliente")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ClienteController {

    private final CriarClienteUseCase criarClienteUseCase;
    private final ClienteRestMapper clienteRestMapper;
    private final KeycloakSyncService keycloakSyncService;

    public ClienteController(CriarClienteUseCase criarClienteUseCase,
                             ClienteRestMapper clienteRestMapper,
                             KeycloakSyncService keycloakSyncService) {
        this.criarClienteUseCase = criarClienteUseCase;
        this.clienteRestMapper = clienteRestMapper;
        this.keycloakSyncService = keycloakSyncService;
    }

    @POST
    public Response criar(@Valid ClienteRequest request) {

        Cliente model = this.clienteRestMapper.toModel(request);
        Cliente resp = this.criarClienteUseCase.run(model);
        this.keycloakSyncService.criarUsuario(model);

        ClienteResponse response = this.clienteRestMapper.toResponse(resp);

        return Response.status(Response.Status.CREATED).entity(response).build();
    }
}
