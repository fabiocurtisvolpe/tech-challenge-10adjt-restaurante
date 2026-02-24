package com.adjt.rest.mapper;

import com.adjt.core.model.Endereco;
import com.adjt.data.mapper.EnderecoMapper;
import com.adjt.rest.dto.request.EnderecoRequest;
import com.adjt.rest.dto.response.EnderecoResponse;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class EnderecoRestMapper {

    @Inject
    EnderecoMapper enderecoMapper;

    public Endereco toModel(EnderecoRequest request) {
        return enderecoMapper.toModel(request);
    }

    public EnderecoResponse toResponse(Endereco model) {
        if (model == null) {
            return null;
        }

        EnderecoResponse response = new EnderecoResponse();
        response.id = model.getId();
        response.rua = model.getRua();
        response.bairro = model.getBairro();
        response.cep = model.getCep();
        response.complemento = model.getComplemento();
        response.numero = model.getNumero();
        response.cidade = model.getCidade();
        response.uf = model.getUf();
        response.principal = model.getPrincipal();
        response.observacao = model.getObservacao();

        return response;
    }
}
