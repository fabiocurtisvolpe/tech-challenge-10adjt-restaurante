package com.adjt.data.mapper;

import java.math.BigDecimal;

public interface CardapioSource {
    Long getId();
    String getNome();
    String getDescricao();
    BigDecimal getPreco();
    String getFoto();
    Boolean getDisponivel();
}
