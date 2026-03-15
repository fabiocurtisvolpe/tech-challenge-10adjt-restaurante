package com.adjt.rest.event;

public record PagamentoResultadoEvent(
        Long pedidoId,
        boolean aprovado,
        Integer statusCode
) {}
