package com.adjt.data.mapper;

import com.adjt.core.model.Endereco;
import com.adjt.data.entity.EnderecoEntity;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class EnderecoMapper {

    public Endereco toModel(EnderecoEntity entity) {
        if (entity == null) {
            return null;
        }

        Endereco endereco = new Endereco();
        endereco.setId(entity.id);
        endereco.setRua(entity.rua);
        endereco.setBairro(entity.bairro);
        endereco.setCep(entity.cep);
        endereco.setComplemento(entity.complemento);
        endereco.setNumero(entity.numero);
        endereco.setCidade(entity.cidade);
        endereco.setUf(entity.uf);
        endereco.setPrincipal(entity.principal);
        endereco.setObservacao(entity.observacao);

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
