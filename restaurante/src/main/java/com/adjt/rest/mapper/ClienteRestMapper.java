package com.adjt.rest.mapper;

import com.adjt.core.model.Usuario;
import com.adjt.data.mapper.UsuarioMapper;
import com.adjt.rest.dto.request.ClienteRequest;
import com.adjt.rest.dto.response.ClienteResponse;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class ClienteRestMapper {

    @Inject
    UsuarioMapper usuarioMapper;

    public Usuario toModel(ClienteRequest request) {
        return usuarioMapper.toModel(request);
    }

    public ClienteResponse toResponse(Usuario model) {
        if (model == null) {
            return null;
        }

        ClienteResponse response = new ClienteResponse();
        response.id = model.getId();
        response.nome = model.getNome();
        response.cpf = model.getCpf();
        response.email = model.getEmail();
        response.senha = model.getSenha();
        response.dtCadastro = model.getDtCadastro();

        return response;
    }
}
