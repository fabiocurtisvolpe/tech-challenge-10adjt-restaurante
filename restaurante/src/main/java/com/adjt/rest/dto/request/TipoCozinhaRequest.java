package com.adjt.rest.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class TipoCozinhaRequest {

    @Positive(message = "O ID deve ser um número positivo maior que zero")
    public Long id;

    @NotBlank(message = "O nome não pode estar em branco")
    @Size(max = 50, message = "O nome deve ter no máximo 50 caracteres")
    public String nome;

    @Size(max = 1000, message = "A descrição deve ter no máximo 1000 caracteres")
    public String descricao;
}
