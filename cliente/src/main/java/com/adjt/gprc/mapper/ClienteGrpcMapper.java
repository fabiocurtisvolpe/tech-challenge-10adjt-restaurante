package com.adjt.gprc.mapper;

import com.adjt.cliente.ClienteUpdateRequest;
import com.adjt.cliente.Endereco;

import java.util.List;
import java.util.stream.Collectors;

public class ClienteGrpcMapper {

    // Proto Cliente → Core Cliente
    public static com.adjt.core.model.Cliente toCoreModel(ClienteUpdateRequest request) {
        com.adjt.core.model.Cliente cliente = new com.adjt.core.model.Cliente();
        cliente.setId(request.getId());
        cliente.setNome(request.getNome());
        cliente.setCpf(request.getCpf());
        cliente.setEmail(request.getEmail());
        cliente.setEnderecos(toEnderecosCoreModel(request.getEnderecosList()));
        return cliente;
    }

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

    private static List<com.adjt.core.model.Endereco> toEnderecosCoreModel(List<Endereco> enderecos) {
        return enderecos.stream().map(e -> {
            com.adjt.core.model.Endereco endereco = new com.adjt.core.model.Endereco();
            endereco.setId(e.getId());
            endereco.setRua(e.getRua());
            endereco.setBairro(e.getBairro());
            endereco.setCep(e.getCep());
            endereco.setComplemento(e.getComplemento());
            endereco.setNumero(e.getNumero());
            endereco.setCidade(e.getCidade());
            endereco.setUf(e.getUf());
            endereco.setPrincipal(e.getPrincipal());
            endereco.setObservacao(e.getObservacao());
            return endereco;
        }).collect(Collectors.toList());
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
