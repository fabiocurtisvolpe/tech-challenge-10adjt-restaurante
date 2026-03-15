package com.adjt.pgto.dto;

public class RequisicaoPagamentoDTO {
    public Long valor;
    public String pagamento_id;
    public String cliente_id;

    public RequisicaoPagamentoDTO(Long valor, String pagamentoId, String clienteId) {
        this.valor = valor;
        this.pagamento_id = pagamentoId;
        this.cliente_id = clienteId;
    }
}
