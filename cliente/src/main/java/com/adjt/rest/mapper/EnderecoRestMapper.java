package com.adjt.rest.mapper;

import com.adjt.core.model.Endereco;
import com.adjt.rest.dto.request.EnderecoRequest;
import com.adjt.rest.dto.response.EnderecoResponse;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class EnderecoRestMapper {

    public Endereco toModel(EnderecoRequest request) {
        if (request == null) {
            return null;
        }

        Endereco endereco = new Endereco();
        endereco.setId(request.id);
        endereco.setRua(request.rua);
        endereco.setBairro(request.bairro);
        endereco.setCep(request.cep);
        endereco.setComplemento(request.complemento);
        endereco.setNumero(request.numero);
        endereco.setCidade(request.cidade);
        endereco.setUf(request.uf);
        endereco.setPrincipal(request.principal);
        endereco.setObservacao(request.observacao);

        return endereco;
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
