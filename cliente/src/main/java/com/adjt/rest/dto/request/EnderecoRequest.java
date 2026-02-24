package com.adjt.rest.dto.request;

import com.adjt.data.mapper.EnderecoSource;

public class EnderecoRequest implements EnderecoSource {

    public Long id;
    public String rua;
    public String bairro;
    public String cep;
    public String complemento;
    public String numero;
    public String cidade;
    public String uf;
    public Boolean principal;
    public String observacao;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public String getRua() {
        return rua;
    }

    @Override
    public String getBairro() {
        return bairro;
    }

    @Override
    public String getCep() {
        return cep;
    }

    @Override
    public String getComplemento() {
        return complemento;
    }

    @Override
    public String getNumero() {
        return numero;
    }

    @Override
    public String getCidade() {
        return cidade;
    }

    @Override
    public String getUf() {
        return uf;
    }

    @Override
    public Boolean getPrincipal() {
        return principal;
    }

    @Override
    public String getObservacao() {
        return observacao;
    }
}
