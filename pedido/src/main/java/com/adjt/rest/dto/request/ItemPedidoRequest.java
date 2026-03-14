package com.adjt.rest.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public class ItemPedidoRequest {

    @NotNull(message = "O ID do cardápio é obrigatório")
    @Positive(message = "O ID do cardápio deve ser um número positivo")
    public Long idCardapio;

    @NotNull(message = "A quantidade é obrigatória")
    @Min(value = 1, message = "A quantidade mínima deve ser 1")
    public Integer qtd;

    @NotNull(message = "O valor é obrigatório")
    @DecimalMin(value = "5.00", message = "O valor deve ser maior que 5")
    public BigDecimal valor;
}
