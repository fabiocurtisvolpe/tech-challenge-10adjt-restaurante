package com.adjt.base.infra.event;

public record PagamentoResultadoEvent(
        Long pedidoId,
        boolean aprovado,
        Integer statusCode
) {}
