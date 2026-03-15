package com.adjt.core.event;

import java.math.BigDecimal;

public record PedidoCriadoEvent(
        Long pedidoId,
        Long clienteId,
        Long restauranteId,
        BigDecimal valorTotal
) {}
