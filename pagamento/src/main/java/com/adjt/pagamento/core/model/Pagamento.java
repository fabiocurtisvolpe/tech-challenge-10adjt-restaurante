package com.adjt.pagamento.core.model;

public class Pagamento {

    private Long pedidoId;
    private Long valor;
    private boolean aprovado;

    public Pagamento(Long pedidoId, Long valor, boolean aprovado) {
        this.pedidoId = pedidoId;
        this.valor = valor;
        this.aprovado = aprovado;
    }

    public Long getPedidoId() {
        return pedidoId;
    }
    public Long getValor() {
        return valor;
    }
    public boolean isAprovado() {
        return aprovado;
    }
}
