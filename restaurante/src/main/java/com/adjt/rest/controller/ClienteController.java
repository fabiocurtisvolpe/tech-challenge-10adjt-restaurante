package com.adjt.rest.controller;

import com.adjt.core.model.Usuario;
import com.adjt.core.usecase.usuario.CriarUsuarioUseCase;
import com.adjt.rest.dto.request.UsuarioRequest;
import com.adjt.rest.dto.response.UsuarioResponse;
import com.adjt.rest.mapper.UsuarioRestMapper;
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
    private final UsuarioRestMapper usuarioRestMapper;

    public ClienteController(CriarUsuarioUseCase criarUsuarioUseCase,
                             UsuarioRestMapper usuarioRestMapper) {
        this.criarUsuarioUseCase = criarUsuarioUseCase;
        this.usuarioRestMapper = usuarioRestMapper;
    }

    @POST
    public Response criar(@Valid UsuarioRequest request) {

        Usuario model = this.usuarioRestMapper.toModel(request);
        Usuario resp = this.criarUsuarioUseCase.run(model);
        UsuarioResponse response = this.usuarioRestMapper.toResponse(resp);

        return Response.status(Response.Status.CREATED).entity(response).build();
    }
}
