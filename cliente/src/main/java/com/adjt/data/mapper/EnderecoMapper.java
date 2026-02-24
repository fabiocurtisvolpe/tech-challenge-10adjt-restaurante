package com.adjt.data.mapper;

import com.adjt.core.model.Endereco;
import com.adjt.data.entity.EnderecoEntity;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class EnderecoMapper {

    public Endereco toModel(EnderecoSource source) {
        if (source == null) {
            return null;
        }

        Endereco endereco = new Endereco();
        endereco.setId(source.getId());
        endereco.setRua(source.getRua());
        endereco.setBairro(source.getBairro());
        endereco.setCep(source.getCep());
        endereco.setComplemento(source.getComplemento());
        endereco.setNumero(source.getNumero());
        endereco.setCidade(source.getCidade());
        endereco.setUf(source.getUf());
        endereco.setPrincipal(source.getPrincipal());
        endereco.setObservacao(source.getObservacao());

        return endereco;
    }

    public EnderecoEntity toEntity(Endereco model) {
        if (model == null) {
            return null;
        }

        EnderecoEntity entity = new EnderecoEntity();
        entity.id = model.getId();
        entity.rua = model.getRua();
        entity.bairro = model.getBairro();
        entity.cep = model.getCep();
        entity.complemento = model.getComplemento();
        entity.numero = model.getNumero();
        entity.cidade = model.getCidade();
        entity.uf = model.getUf();
        entity.principal = model.getPrincipal();
        entity.observacao = model.getObservacao();

        return entity;
    }
}
