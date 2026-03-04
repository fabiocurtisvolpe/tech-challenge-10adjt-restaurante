package com.adjt.rest.mapper;

import com.adjt.core.model.Usuario;
import com.adjt.data.mapper.UsuarioMapper;
import com.adjt.rest.dto.request.UsuarioRequest;
import com.adjt.rest.dto.response.UsuarioResponse;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UsuarioRestMapper {

    private final UsuarioMapper usuarioMapper;

    public UsuarioRestMapper(UsuarioMapper usuarioMapper) {
        this.usuarioMapper = usuarioMapper;
    }

    public Usuario toModel(UsuarioRequest request) {
        return usuarioMapper.toModel(request);
    }

    public UsuarioResponse toResponse(Usuario model) {
        if (model == null) {
            return null;
        }

        UsuarioResponse response = new UsuarioResponse();
        response.id = model.getId();
        response.nome = model.getNome();
        response.cpf = model.getCpf();
        response.email = model.getEmail();
        response.dtCadastro = model.getDtCadastro();
        response.idPerfil = model.getPerfil().getId();

        return response;
    }
}
