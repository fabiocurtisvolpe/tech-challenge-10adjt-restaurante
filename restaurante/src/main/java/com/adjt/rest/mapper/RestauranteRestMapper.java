package com.adjt.rest.mapper;

import com.adjt.core.model.Cardapio;
import com.adjt.core.model.Endereco;
import com.adjt.core.model.Restaurante;
import com.adjt.rest.dto.request.RestauranteRequest;
import com.adjt.rest.dto.response.RestauranteResponse;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class RestauranteRestMapper {

    private final TipoCozinhaRestMapper tipoCozinhaMapper;
    private final CardapioRestMapper cardapioMapper;
    private final EnderecoRestMapper enderecoMapper;

    public RestauranteRestMapper(TipoCozinhaRestMapper tipoCozinhaMapper,
                                 CardapioRestMapper cardapioMapper,
                                 EnderecoRestMapper enderecoMapper) {
        this.tipoCozinhaMapper = tipoCozinhaMapper;
        this.cardapioMapper = cardapioMapper;
        this.enderecoMapper = enderecoMapper;
    }

    public Restaurante toModel(RestauranteRequest request) {
        if (request == null) {
            return null;
        }

        Restaurante restaurante = new Restaurante();
        restaurante.setId(request.id);
        restaurante.setNome(request.nome);
        restaurante.setDescricao(request.descricao);

        String horarioFuncionamento = HorarioFuncionamentoRestMapper.converterParaJson(request.horarioFuncionamento);
        restaurante.setHorarioFuncionamento(horarioFuncionamento);

        if (request.enderecos != null) {
            List<Endereco> enderecos = request.enderecos.stream()
                    .map(enderecoMapper::toModel)
                    .collect(Collectors.toList());
            restaurante.setEnderecos(enderecos);
        }

        if (request.cardapios != null) {
            List<Cardapio> cardapios = request.cardapios.stream()
                    .map(cardapioMapper::toModel)
                    .collect(Collectors.toList());
            restaurante.setCardapios(cardapios);
        }

        return restaurante;

    }

    public RestauranteResponse toResponse(Restaurante model) {

        if (model == null) {
            return null;
        }

        RestauranteResponse restaurante = new RestauranteResponse();
        restaurante.id = model.getId();
        restaurante.nome = model.getNome();
        restaurante.descricao = model.getDescricao();
        restaurante.horarioFuncionamento = model.getHorarioFuncionamento();
        restaurante.idDono = model.getDono().getId();

        if (model.getTipoCozinha() != null) {
            restaurante.tipoCozinha = tipoCozinhaMapper.toResponse(model.getTipoCozinha());
        }

        if (!model.getCardapios().isEmpty()) {
            restaurante.cardapios = model.getCardapios().stream()
                    .map(cardapio -> cardapioMapper.toResponse(cardapio, model.getId()))
                    .collect(Collectors.toList());
        }

        if (!model.getEnderecos().isEmpty()) {
            restaurante.enderecos = model.getEnderecos().stream()
                    .map(endereco ->  enderecoMapper.toResponse(endereco, model.getId()))
                    .collect(Collectors.toList());
        }

        return restaurante;
    }
}
