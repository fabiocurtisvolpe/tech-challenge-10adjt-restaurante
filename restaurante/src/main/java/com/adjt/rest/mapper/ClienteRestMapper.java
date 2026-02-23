package com.adjt.rest.mapper;

import com.adjt.core.model.Endereco;
import com.adjt.core.model.Usuario;
import com.adjt.rest.dto.request.ClienteRequest;
import com.adjt.rest.dto.response.ClienteResponse;
import com.adjt.rest.dto.response.EnderecoResponse;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class ClienteRestMapper {

    @Inject
    EnderecoRestMapper enderecoRestMapper;

    public Usuario toModel(ClienteRequest request) {
        if (request == null) {
            return null;
        }

        Usuario usuario = new Usuario();
        usuario.setId(request.id);
        usuario.setNome(request.nome);
        usuario.setCpf(request.cpf);
        usuario.setEmail(request.email);
        usuario.setSenha(request.senha);
        usuario.setDtCadastro(request.dtCadastro);

        if (request.enderecos != null) {
            List<Endereco> enderecos = request.enderecos.stream()
                    .map(enderecoRestMapper::toModel)
                    .collect(Collectors.toList());
            usuario.setEnderecos(enderecos);
        }

        return usuario;
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

        if (model.getEnderecos() != null) {
            List<EnderecoResponse> enderecos = model.getEnderecos().stream()
                    .map(enderecoRestMapper::toResponse)
                    .collect(Collectors.toList());
            response.enderecos = enderecos;
        }

        return response;
    }
}
