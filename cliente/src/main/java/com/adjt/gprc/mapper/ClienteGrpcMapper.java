package com.adjt.gprc.mapper;

import com.adjt.cliente.Endereco;

import java.util.List;
import java.util.stream.Collectors;

public class ClienteGrpcMapper {

    // Core Cliente → Proto Cliente
    public static com.adjt.cliente.Cliente toProto(com.adjt.core.model.Cliente cliente) {
        return com.adjt.cliente.Cliente.newBuilder()
                .setId(cliente.getId())
                .setNome(cliente.getNome())
                .setCpf(cliente.getCpf())
                .setEmail(cliente.getEmail())
                .addAllEnderecos(toEnderecosProto(cliente.getEnderecos()))
                .build();
    }

    private static List<Endereco> toEnderecosProto(List<com.adjt.core.model.Endereco> enderecos) {
        return enderecos.stream().map(e ->
                Endereco.newBuilder()
                        .setId(e.getId())
                        .setRua(e.getRua())
                        .setBairro(e.getBairro())
                        .setCep(e.getCep())
                        .setComplemento(e.getComplemento())
                        .setNumero(e.getNumero())
                        .setCidade(e.getCidade())
                        .setUf(e.getUf())
                        .setPrincipal(e.getPrincipal())
                        .setObservacao(e.getObservacao())
                        .build()
        ).collect(Collectors.toList());
    }
}
