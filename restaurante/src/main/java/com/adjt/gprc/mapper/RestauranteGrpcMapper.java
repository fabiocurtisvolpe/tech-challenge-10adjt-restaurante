package com.adjt.gprc.mapper;

import com.adjt.core.model.Cardapio;
import com.adjt.core.model.Endereco;
import com.adjt.core.model.Restaurante;
import com.adjt.core.model.TipoCozinha;
import com.adjt.restaurante.ItemCardapioResponse;
import com.adjt.restaurante.ListarItensCardapioResponse;
import com.adjt.restaurante.ListarRestaurantesResponse;
import com.adjt.restaurante.RestauranteResponse;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class RestauranteGrpcMapper {

    // =========================================================
    // Proto → Core Model
    // =========================================================

    public static Restaurante toCoreModel(RestauranteResponse proto) {
        if (proto == null) return null;

        Restaurante restaurante = new Restaurante();
        restaurante.setId(proto.getId());
        restaurante.setNome(proto.getNome());
        restaurante.setDescricao(proto.getDescricao());
        restaurante.setHorarioFuncionamento(proto.getHorarioFuncionamento());
        restaurante.setTipoCozinha(toCoreModelTipoCozinha(proto.getTipoCozinha()));
        restaurante.setEnderecos(
                proto.hasEndereco()
                        ? Collections.singletonList(toCoreModelEndereco(proto.getEndereco()))
                        : Collections.emptyList()
        );
        return restaurante;
    }

    public static List<Restaurante> toCoreModelList(ListarRestaurantesResponse proto) {
        if (proto == null) return Collections.emptyList();

        return proto.getRestaurantesList()
                .stream()
                .map(RestauranteGrpcMapper::toCoreModel)
                .collect(Collectors.toList());
    }

    public static TipoCozinha toCoreModelTipoCozinha(com.adjt.restaurante.TipoCozinha proto) {
        if (proto == null) return null;

        TipoCozinha tipoCozinha = new TipoCozinha();
        tipoCozinha.setId(proto.getId());
        tipoCozinha.setNome(proto.getNome());
        tipoCozinha.setDescricao(proto.getDescricao());
        return tipoCozinha;
    }

    public static Endereco toCoreModelEndereco(com.adjt.restaurante.Endereco proto) {
        if (proto == null) return null;

        Endereco endereco = new Endereco();
        endereco.setId(proto.getId());
        endereco.setRua(proto.getRua());
        endereco.setBairro(proto.getBairro());
        endereco.setCep(proto.getCep());
        endereco.setComplemento(proto.getComplemento());
        endereco.setNumero(proto.getNumero());
        endereco.setCidade(proto.getCidade());
        endereco.setUf(proto.getUf());
        endereco.setObservacao(proto.getObservacao());
        return endereco;
    }

    public static Cardapio toCoreModelCardapio(ItemCardapioResponse proto) {
        if (proto == null) return null;

        Cardapio cardapio = new Cardapio();
        cardapio.setId(proto.getId());
        cardapio.setNome(proto.getNome());
        cardapio.setDescricao(proto.getDescricao());
        cardapio.setPreco(new BigDecimal(proto.getPreco()));
        cardapio.setFoto(proto.getFoto());
        cardapio.setDisponivel(proto.getDisponivel());
        return cardapio;
    }

    public static List<Cardapio> toCoreModelCardapioList(ListarItensCardapioResponse proto) {
        if (proto == null) return Collections.emptyList();

        return proto.getItensList()
                .stream()
                .map(RestauranteGrpcMapper::toCoreModelCardapio)
                .collect(Collectors.toList());
    }

    // =========================================================
    // Core Model → Proto Response
    // =========================================================

    public static RestauranteResponse toProtoResponse(Restaurante restaurante) {
        if (restaurante == null) return null;

        RestauranteResponse.Builder builder = RestauranteResponse.newBuilder()
                .setId(restaurante.getId())
                .setNome(restaurante.getNome())
                .setDescricao(restaurante.getDescricao())
                .setHorarioFuncionamento(restaurante.getHorarioFuncionamento());

        if (restaurante.getTipoCozinha() != null) {
            builder.setIdTipoCozinha(restaurante.getTipoCozinha().getId());
            builder.setTipoCozinha(toProtoTipoCozinha(restaurante.getTipoCozinha()));
        }

        if (restaurante.getDono() != null) {
            builder.setIdUsuario(restaurante.getDono().getId());
        }

        if (restaurante.getEnderecos() != null && !restaurante.getEnderecos().isEmpty()) {
            builder.setEndereco(toProtoEndereco(restaurante.getEnderecos().getFirst()));
        }

        return builder.build();
    }

    public static ListarRestaurantesResponse toProtoListResponse(List<Restaurante> restaurantes) {
        if (restaurantes == null) return ListarRestaurantesResponse.newBuilder().setTotal(0).build();

        List<RestauranteResponse> responses = restaurantes.stream()
                .map(RestauranteGrpcMapper::toProtoResponse)
                .collect(Collectors.toList());

        return ListarRestaurantesResponse.newBuilder()
                .addAllRestaurantes(responses)
                .setTotal(responses.size())
                .build();
    }

    public static ItemCardapioResponse toProtoItemResponse(Cardapio cardapio) {
        if (cardapio == null) return null;

        return ItemCardapioResponse.newBuilder()
                .setId(cardapio.getId())
                .setNome(cardapio.getNome())
                .setDescricao(cardapio.getDescricao())
                .setPreco(cardapio.getPreco() != null ? cardapio.getPreco().toPlainString() : "0.00")
                .setFoto(cardapio.getFoto() != null ? cardapio.getFoto() : "")
                .setDisponivel(cardapio.getDisponivel() != null && cardapio.getDisponivel())
                .setIdRestaurante(cardapio.getRestaurante() != null ? cardapio.getRestaurante().getId() : 0L)
                .build();
    }

    public static ListarItensCardapioResponse toProtoListCardapioResponse(List<Cardapio> cardapios) {
        if (cardapios == null) return ListarItensCardapioResponse.newBuilder().setTotal(0).build();

        List<ItemCardapioResponse> responses = cardapios.stream()
                .map(RestauranteGrpcMapper::toProtoItemResponse)
                .collect(Collectors.toList());

        return ListarItensCardapioResponse.newBuilder()
                .addAllItens(responses)
                .setTotal(responses.size())
                .build();
    }

    // =========================================================
    // Core Model → Proto (tipos internos)
    // =========================================================

    public static com.adjt.restaurante.TipoCozinha toProtoTipoCozinha(TipoCozinha tipoCozinha) {
        if (tipoCozinha == null) return null;

        return com.adjt.restaurante.TipoCozinha.newBuilder()
                .setId(tipoCozinha.getId())
                .setNome(tipoCozinha.getNome())
                .setDescricao(tipoCozinha.getDescricao())
                .build();
    }

    public static com.adjt.restaurante.Endereco toProtoEndereco(Endereco endereco) {
        if (endereco == null) return null;

        return com.adjt.restaurante.Endereco.newBuilder()
                .setId(endereco.getId())
                .setRua(endereco.getRua())
                .setBairro(endereco.getBairro())
                .setCep(endereco.getCep())
                .setComplemento(endereco.getComplemento() != null ? endereco.getComplemento() : "")
                .setNumero(endereco.getNumero())
                .setCidade(endereco.getCidade())
                .setUf(endereco.getUf())
                .setObservacao(endereco.getObservacao() != null ? endereco.getObservacao() : "")
                .build();
    }
}
