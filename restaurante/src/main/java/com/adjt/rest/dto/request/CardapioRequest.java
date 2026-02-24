package com.adjt.rest.dto.request;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public class CardapioRequest {

    @Positive(message = "O ID deve ser um número positivo maior que zero")
    public Long id;

    @NotBlank(message = "O nome não pode estar em branco")
    @Size(max = 50, message = "O nome deve ter no máximo 50 caracteres")
    public String nome;

    @Size(max = 1000, message = "A descrição deve ter no máximo 1000 caracteres")
    public String descricao;

    @NotNull(message = "O preço não pode ser vazio/nulo")
    @DecimalMin(value = "0.0", inclusive = false, message = "O preço deve ser maior que zero")
    public BigDecimal preco;

    @NotBlank(message = "A foto não pode estar em branco")
    public String foto;

    @NotNull(message = "O disponível não pode ser vazio/nulo")
    public Boolean disponivel;

    @NotNull(message = "O id restaurante não pode estar vazio")
    @Positive(message = "O id restaurante deve ser maior que zero")
    public Long idRestaurante;
}
