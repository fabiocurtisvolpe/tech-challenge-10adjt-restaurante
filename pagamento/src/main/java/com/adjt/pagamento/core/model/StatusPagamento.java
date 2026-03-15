package com.adjt.pagamento.core.model;

public enum StatusPagamento {

    APROVADO(201),
    PENDENTE(202),
    RECUSADO(402),
    ERRO_GATEWAY(500),
    SERVICO_INDISPONIVEL(503);

    private final int httpStatus;

    StatusPagamento(int httpStatus) {
        this.httpStatus = httpStatus;
    }

    public int getHttpStatus() {
        return httpStatus;
    }

    public boolean isAprovado() {
        return this == APROVADO;
    }

    public static StatusPagamento fromHttpStatus(int status) {
        for (StatusPagamento s : values()) {
            if (s.httpStatus == status) {
                return s;
            }
        }
        return ERRO_GATEWAY; // fallback para status desconhecido
    }
}
