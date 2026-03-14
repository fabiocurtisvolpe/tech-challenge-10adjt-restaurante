package com.adjt.rest.dto.response;

import com.adjt.core.model.ItemPedido;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PedidoResponse {

    public Long id;
    public LocalDateTime dtCadastro;
    public Long idCliente;
    public Long idRestaurante;
    public BigDecimal valorTotal;
    public boolean pgtoEfetuado;

    public List<ItemPedido> itens = new ArrayList<>();
}
