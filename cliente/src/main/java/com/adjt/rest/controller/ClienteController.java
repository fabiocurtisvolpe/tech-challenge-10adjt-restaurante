package com.adjt.rest.controller;

import com.adjt.core.model.Cliente;
import com.adjt.core.usecase.cliente.CriarClienteUseCase;
import com.adjt.rest.dto.request.ClienteRequest;
import com.adjt.rest.mapper.ClienteRestMapper;
import com.adjt.rest.mapper.EnderecoRestMapper;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/cliente")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ClienteController {

    private final CriarClienteUseCase criarClienteUseCase;
    private final ClienteRestMapper clienteRestMapper;
    private final EnderecoRestMapper enderecoRestMapper;

    public ClienteController(CriarClienteUseCase criarClienteUseCase,
                             ClienteRestMapper clienteRestMapper,
                             EnderecoRestMapper enderecoRestMapper) {
        this.criarClienteUseCase = criarClienteUseCase;
        this.clienteRestMapper = clienteRestMapper;
        this.enderecoRestMapper = enderecoRestMapper;
    }

    @POST
    public Response criar(@Valid ClienteRequest request) {

        Cliente model = this.clienteRestMapper.toModel(request);
        Cliente resp = criarClienteUseCase.run(model);


        return Response.status(Response.Status.CREATED).entity(null).build();
    }
}
