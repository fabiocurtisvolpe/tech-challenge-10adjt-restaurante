package com.adjt.data.mapper;

import com.adjt.core.model.Cardapio;
import com.adjt.core.model.Endereco;
import com.adjt.core.model.Restaurante;
import com.adjt.data.entity.RestauranteEntity;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@ApplicationScoped
public class RestauranteMapper {

    private final TipoCozinhaMapper tipoCozinhaMapper;
    private final UsuarioMapper usuarioMapper;
    private final EnderecoMapper enderecoMapper;
    private final CardapioMapper cardapioMapper;

    public RestauranteMapper(TipoCozinhaMapper tipoCozinhaMapper,
                             UsuarioMapper usuarioMapper,
                             EnderecoMapper enderecoMapper,
                             CardapioMapper cardapioMapper) {
        this.tipoCozinhaMapper = tipoCozinhaMapper;
        this.usuarioMapper = usuarioMapper;
        this.enderecoMapper = enderecoMapper;
        this.cardapioMapper = cardapioMapper;
    }

    public Restaurante toModel(RestauranteEntity entity) {
        if (entity == null) {
            return null;
        }

        Restaurante restaurante = new Restaurante();
        restaurante.setId(entity.id);
        restaurante.setNome(entity.nome);
        restaurante.setDescricao(entity.descricao);
        restaurante.setHorarioFuncionamento(entity.horarioFuncionamento);

        if (entity.tipoCozinha != null) {
            restaurante.setTipoCozinha(tipoCozinhaMapper.toModel(entity.tipoCozinha));
        }

        if (entity.usuario != null) {
            restaurante.setDono(usuarioMapper.toModel(entity.usuario, false));
        }

        if (!entity.enderecos.isEmpty()) {
            List<Endereco> enderecos = entity.enderecos.stream()
                    .map(enderecoMapper::toModel)
                    .collect(Collectors.toList());
            restaurante.setEnderecos(enderecos);
        }

        if (!entity.cardapios.isEmpty()) {
            List<Cardapio> cardapios = entity.cardapios.stream()
                    .filter(Objects::nonNull)
                    .map(cardapioMapper::toModel)
                    .collect(Collectors.toList());
            restaurante.setCardapios(cardapios);
        }
        return restaurante;
    }

    public RestauranteEntity toEntity(Restaurante model) {
        if (model == null) {
            return null;
        }

        RestauranteEntity entity = new RestauranteEntity();
        entity.id = model.getId();
        entity.nome = model.getNome();
        entity.descricao = model.getDescricao();
        entity.horarioFuncionamento = model.getHorarioFuncionamento();

        if (model.getTipoCozinha() != null) {
            entity.tipoCozinha = tipoCozinhaMapper.toEntity(model.getTipoCozinha());
        }

        if (model.getDono() != null) {
            entity.usuario = usuarioMapper.toEntity(model.getDono());
        }

        if (model.getEnderecos() != null) {
            model.getEnderecos().stream()
                    .map(enderecoMapper::toEntity)
                    .forEach(entity::addEndereco);
        }

        if (model.getCardapios() != null) {
            model.getCardapios().stream()
                    .map(cardapioMapper::toEntity)
                    .forEach(entity::addCardapio);
        }

        return entity;
    }
}
