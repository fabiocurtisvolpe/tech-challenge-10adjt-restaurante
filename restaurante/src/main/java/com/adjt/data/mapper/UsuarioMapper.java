package com.adjt.data.mapper;

import com.adjt.core.model.Restaurante;
import com.adjt.core.model.Usuario;
import com.adjt.data.entity.PerfilEntity;
import com.adjt.data.entity.UsuarioEntity;
import com.adjt.data.repository.jpa.PerfilRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class UsuarioMapper {

    private final PerfilMapper perfilMapper;
    private final PerfilRepository perfilRepository;

    private final RestauranteMapper restauranteMapper;

    public UsuarioMapper(PerfilMapper perfilMapper,
                         RestauranteMapper restauranteMapper,
                         PerfilRepository perfilRepository) {
        this.perfilMapper = perfilMapper;
        this.restauranteMapper = restauranteMapper;
        this.perfilRepository = perfilRepository;
    }

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
        usuario.setDtCadastro(source.getDtCadastro());
        usuario.setKeycloakId(source.getKeycloakId());

        if (includeRelationships && source instanceof UsuarioEntity entity) {

            if (entity.restaurantes != null) {
                List<Restaurante> restaurantes = entity.restaurantes.stream()
                        .map(restauranteMapper::toModel)
                        .collect(Collectors.toList());
                usuario.setRestaurantes(restaurantes);
            }

            PerfilEntity perfilEntity = perfilRepository.findById(source.getPerfilId());
            usuario.setPerfil(perfilMapper.toModel(perfilEntity));
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
        entity.dtCadastro = model.getDtCadastro();
        entity.keycloakId = model.getKeycloakId();

        if (model.getRestaurantes() != null) {
            model.getRestaurantes().stream()
                    .map(restauranteMapper::toEntity)
                    .forEach(entity::addRestaurante);
        }

        entity.perfil = perfilRepository.findById(model.getPerfil().getId());

        return entity;
    }
}