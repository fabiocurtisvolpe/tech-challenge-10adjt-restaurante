package com.adjt.core.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.math.BigDecimal;

public class ItemPedido implements Serializable {

    protected Long id;

    @JsonIgnore
    protected Pedido pedido;

    protected Long idCardapio;
    protected Integer qtd;
    protected BigDecimal valor;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public Long getIdCardapio() {
        return idCardapio;
    }

    public void setIdCardapio(Long idCardapio) {
        this.idCardapio = idCardapio;
    }

    public Integer getQtd() {
        return qtd;
    }

    public void setQtd(Integer qtd) {
        this.qtd = qtd;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }
}
