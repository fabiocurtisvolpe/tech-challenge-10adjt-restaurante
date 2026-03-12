package com.adjt.gprc.mapper;

import com.adjt.core.model.CardapioInfo;
import com.adjt.core.model.EnderecoRestauranteInfo;
import com.adjt.core.model.RestauranteInfo;
import com.adjt.core.model.TipoCozinhaInfo;
import com.adjt.restaurante.ItemCardapioResponse;
import com.adjt.restaurante.ListarItensCardapioResponse;
import com.adjt.restaurante.ListarRestaurantesResponse;
import com.adjt.restaurante.RestauranteResponse;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class RestaurantePedidoMapper {

    // =========================================================
    // Proto → Core Model (usado no serviço pedido)
    // =========================================================

    public static RestauranteInfo toRestauranteInfo(RestauranteResponse proto) {
        if (proto == null) return null;

        RestauranteInfo info = new RestauranteInfo();
        info.setId(proto.getId());
        info.setNome(proto.getNome());
        info.setDescricao(proto.getDescricao());
        info.setHorarioFuncionamento(proto.getHorarioFuncionamento());
        info.setIdTipoCozinha(proto.getIdTipoCozinha());
        info.setIdUsuario(proto.getIdUsuario());
        info.setTipoCozinha(toTipoCozinhaInfo(proto.getTipoCozinha()));

        if (proto.hasEndereco()) {
            info.setEndereco(toEnderecoInfo(proto.getEndereco()));
        }

        return info;
    }

    public static List<RestauranteInfo> toRestauranteInfoList(ListarRestaurantesResponse proto) {
        if (proto == null) return Collections.emptyList();

        return proto.getRestaurantesList()
                .stream()
                .map(RestaurantePedidoMapper::toRestauranteInfo)
                .collect(Collectors.toList());
    }

    public static TipoCozinhaInfo toTipoCozinhaInfo(com.adjt.restaurante.TipoCozinha proto) {
        if (proto == null) return null;

        TipoCozinhaInfo info = new TipoCozinhaInfo();
        info.setId(proto.getId());
        info.setNome(proto.getNome());
        info.setDescricao(proto.getDescricao());
        return info;
    }

    public static EnderecoRestauranteInfo toEnderecoInfo(com.adjt.restaurante.Endereco proto) {
        if (proto == null) return null;

        EnderecoRestauranteInfo info = new EnderecoRestauranteInfo();
        info.setId(proto.getId());
        info.setRua(proto.getRua());
        info.setBairro(proto.getBairro());
        info.setCep(proto.getCep());
        info.setCidade(proto.getCidade());
        info.setUf(proto.getUf());
        info.setNumero(proto.getNumero());
        info.setComplemento(proto.getComplemento());
        info.setObservacao(proto.getObservacao());
        info.setIdRestaurante(proto.getIdRestaurante());
        return info;
    }

    public static CardapioInfo toCardapioInfo(ItemCardapioResponse proto) {
        if (proto == null) return null;

        CardapioInfo info = new CardapioInfo();
        info.setId(proto.getId());
        info.setNome(proto.getNome());
        info.setDescricao(proto.getDescricao());
        info.setPreco(!proto.getPreco().isEmpty() ? new BigDecimal(proto.getPreco()) : BigDecimal.ZERO);
        info.setFoto(proto.getFoto());
        info.setDisponivel(proto.getDisponivel());
        info.setIdRestaurante(proto.getIdRestaurante());
        return info;
    }

    public static List<CardapioInfo> toCardapioInfoList(ListarItensCardapioResponse proto) {
        if (proto == null) return Collections.emptyList();

        return proto.getItensList()
                .stream()
                .map(RestaurantePedidoMapper::toCardapioInfo)
                .collect(Collectors.toList());
    }
}
