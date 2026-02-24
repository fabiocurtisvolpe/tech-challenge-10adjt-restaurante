package com.adjt.data.mapper;

import com.adjt.core.model.Restaurante;
import com.adjt.core.model.Usuario;
import com.adjt.data.entity.UsuarioEntity;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class UsuarioMapper {

    @Inject
    PerfilMapper perfilMapper;
    @Inject
    RestauranteMapper restauranteMapper;

    public Usuario toModel(UsuarioSource source) {
        return toModel(source, true);
    }

    public Usuario toModel(UsuarioSource source, boolean includeRelationships) {
        if (source == null) {
            return null;
        }

        Usuario usuario = new Usuario();
        usuario.setId(source.getId());
        usuario.setNome(source.getNome());
        usuario.setCpf(source.getCpf());
        usuario.setEmail(source.getEmail());
        usuario.setSenha(source.getSenha());
        usuario.setDtCadastro(source.getDtCadastro());

        if (includeRelationships && source instanceof UsuarioEntity entity) {

            if (entity.restaurantes != null) {
                List<Restaurante> restaurantes = entity.restaurantes.stream()
                        .map(restauranteMapper::toModel)
                        .collect(Collectors.toList());
                usuario.setRestaurantes(restaurantes);
            }

            if (entity.perfil != null) {
                usuario.setPerfil(perfilMapper.toModel(entity.perfil));
            }
        }

        return usuario;
    }

    public UsuarioEntity toEntity(Usuario model) {
        if (model == null) {
            return null;
        }

        UsuarioEntity entity = new UsuarioEntity();
        entity.id = model.getId();
        entity.nome = model.getNome();
        entity.cpf = model.getCpf();
        entity.email = model.getEmail();
        entity.senha = model.getSenha();
        entity.dtCadastro = model.getDtCadastro();

        if (model.getRestaurantes() != null) {
            model.getRestaurantes().stream()
                    .map(restauranteMapper::toEntity)
                    .forEach(entity::addRestaurante);
        }

        if (model.getPerfil() != null) {
            entity.perfil = perfilMapper.toEntity(model.getPerfil());
        }

        return entity;
    }
}