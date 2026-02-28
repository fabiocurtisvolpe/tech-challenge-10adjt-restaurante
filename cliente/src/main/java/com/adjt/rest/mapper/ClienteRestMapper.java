package com.adjt.rest.mapper;

import com.adjt.core.model.Cliente;
import com.adjt.core.model.Endereco;
import com.adjt.data.mapper.ClienteMapper;
import com.adjt.rest.dto.request.ClienteRequest;
import com.adjt.rest.dto.response.ClienteResponse;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class ClienteRestMapper {

    @Inject
    ClienteMapper clienteMapper;

    @Inject
    EnderecoRestMapper enderecoRestMapper;

    public Cliente toModel(ClienteRequest request) {
        if (request == null) {
            return null;
        }

        Cliente cliente = clienteMapper.toModel(request);

        // Mapear endereços do request
        if (request.enderecos != null) {
            List<Endereco> enderecos = request.enderecos.stream()
                    .map(enderecoRestMapper::toModel)
                    .collect(Collectors.toList());
            cliente.setEnderecos(enderecos);
        }

        return cliente;
    }

    public ClienteResponse toResponse(Cliente model) {
        if (model == null) {
            return null;
        }

        ClienteResponse response = new ClienteResponse();
        response.id = model.getId();
        response.nome = model.getNome();
        response.cpf = model.getCpf();
        response.email = model.getEmail();
        response.dtCadastro = model.getDtCadastro();

        if (model.getEnderecos() != null) {
            response.enderecos = model.getEnderecos().stream()
                    .map(enderecoRestMapper::toResponse)
                    .collect(Collectors.toList());
        }

        return response;
    }
}
