package com.adjt.rest.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.ArrayList;
import java.util.List;

public class PedidoRequest {

    @NotNull(message = "O ID do cliente é obrigatório")
    @Positive(message = "O ID do cliente deve ser um número positivo")
    public Long idCliente;

    @NotNull(message = "O ID do restaurante é obrigatório")
    @Positive(message = "O ID do restaurante deve ser um número positivo")
    public Long idRestaurante;

    @NotEmpty(message = "O pedido deve conter pelo menos um item")
    @Valid
    public List<ItemPedidoRequest> itens = new ArrayList<>();
}
