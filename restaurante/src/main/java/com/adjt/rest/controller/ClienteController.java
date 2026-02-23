package com.adjt.rest.controller;

import com.adjt.core.model.Usuario;
import com.adjt.core.usecase.usuario.CriarUsuarioUseCase;
import com.adjt.rest.dto.request.ClienteRequest;
import com.adjt.rest.dto.response.ClienteResponse;
import com.adjt.rest.mapper.ClienteRestMapper;
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

    private final CriarUsuarioUseCase criarUsuarioUseCase;
    private final ClienteRestMapper clienteRestMapper;

    public ClienteController(CriarUsuarioUseCase criarUsuarioUseCase,
                             ClienteRestMapper clienteRestMapper) {
        this.criarUsuarioUseCase = criarUsuarioUseCase;
        this.clienteRestMapper = clienteRestMapper;
    }

    @POST
    public Response criar(@Valid ClienteRequest request) {

        Usuario model = this.clienteRestMapper.toModel(request);
        Usuario resp = this.criarUsuarioUseCase.run(model);
        ClienteResponse response = this.clienteRestMapper.toResponse(resp);

        return Response.status(Response.Status.CREATED).entity(response).build();
    }
}
