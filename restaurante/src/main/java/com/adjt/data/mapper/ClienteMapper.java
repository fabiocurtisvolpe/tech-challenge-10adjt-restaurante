package com.adjt.data.mapper;

import com.adjt.core.model.Endereco;
import com.adjt.core.model.Usuario;
import com.adjt.data.entity.ClienteEntity;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class ClienteMapper {

    @Inject
    EnderecoMapper enderecoMapper;

    @Inject
    PerfilMapper perfilMapper;

    public Usuario toModel(ClienteEntity entity) {
        if (entity == null) {
            return null;
        }

        Usuario usuario = new Usuario();
        usuario.setId(entity.id);
        usuario.setNome(entity.nome);
        usuario.setCpf(entity.cpf);
        usuario.setEmail(entity.email);
        usuario.setSenha(entity.senha);
        usuario.setDtCadastro(entity.dtCadastro);

        if (entity.enderecos != null) {
            List<Endereco> enderecos = entity.enderecos.stream()
                    .map(enderecoMapper::toModel)
                    .collect(Collectors.toList());
            usuario.setEnderecos(enderecos);
        }

        if (entity.perfil != null) {
            usuario.setPerfil(perfilMapper.toModel(entity.perfil));
        }

        return usuario;
    }

    public ClienteEntity toEntity(Usuario model) {
        if (model == null) {
            return null;
        }

        ClienteEntity entity = new ClienteEntity();
        entity.id = model.getId();
        entity.nome = model.getNome();
        entity.cpf = model.getCpf();
        entity.email = model.getEmail();
        entity.senha = model.getSenha();
        entity.dtCadastro = model.getDtCadastro();

        if (model.getEnderecos() != null) {
            model.getEnderecos().stream()
                    .map(enderecoMapper::toEntity)
                    .forEach(entity::addEndereco);
        }

        if (model.getPerfil() != null) {
            entity.perfil = perfilMapper.toEntity(model.getPerfil());
        }

        return entity;
    }
}