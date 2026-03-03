package com.adjt.rest.controller;

import com.adjt.core.model.Usuario;
import com.adjt.core.usecase.usuario.CriarUsuarioUseCase;
import com.adjt.rest.dto.request.UsuarioRequest;
import com.adjt.rest.dto.response.UsuarioResponse;
import com.adjt.rest.mapper.UsuarioRestMapper;
import com.adjt.service.KeycloakSyncService;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.UUID;

@Path("/cliente")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UsuarioController {

    private final CriarUsuarioUseCase criarUsuarioUseCase;
    private final UsuarioRestMapper usuarioRestMapper;

    private final KeycloakSyncService keycloakSyncService;

    public UsuarioController(CriarUsuarioUseCase criarUsuarioUseCase,
                             UsuarioRestMapper usuarioRestMapper,
                             KeycloakSyncService keycloakSyncService) {
        this.criarUsuarioUseCase = criarUsuarioUseCase;
        this.usuarioRestMapper = usuarioRestMapper;
        this.keycloakSyncService = keycloakSyncService;
    }

    @POST
    public Response criar(@Valid UsuarioRequest request) {

        Usuario model = this.usuarioRestMapper.toModel(request);
        UUID keycloakId = this.keycloakSyncService.criarUsuario(model, request.senha);
        model.setKeycloakId(keycloakId);

        Usuario resp = this.criarUsuarioUseCase.run(model);

        ClienteResponse response = this.clienteRestMapper.toResponse(resp);

        return Response.status(Response.Status.CREATED).entity(response).build();
    }
}
