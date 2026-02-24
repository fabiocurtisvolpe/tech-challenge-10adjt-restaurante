package com.adjt.data.mapper;

public interface EnderecoSource {
    Long getId();
    String getRua();
    String getBairro();
    String getCep();
    String getComplemento();
    String getNumero();
    String getCidade();
    String getUf();
    Boolean getPrincipal();
    String getObservacao();
}
