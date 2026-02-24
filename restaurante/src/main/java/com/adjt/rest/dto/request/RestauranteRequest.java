package com.adjt.rest.dto.request;

import com.adjt.rest.dto.DiaFuncionamento;
import jakarta.validation.constraints.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RestauranteRequest {

    @Positive(message = "O ID deve ser um número positivo maior que zero")
    public Long id;

    @NotBlank(message = "O nome não pode estar em branco")
    @Size(max = 50, message = "O nome deve ter no máximo 50 caracteres")
    public String nome;

    @Size(max = 1000, message = "A descrição deve ter no máximo 1000 caracteres")
    public String descricao;

    @NotNull(message = "O horário de funcionamento não pode estar em branco")
    protected Map<String, DiaFuncionamento> horarioFuncionamento;

    @NotNull(message = "O id tipo de cozinha não pode estar vazio")
    @Positive(message = "O id tipo de cozinha deve ser maior que zero")
    public Long idTipoCozinha;

    @NotNull(message = "O id usuario não pode estar vazio")
    @Positive(message = "O id usuario deve ser maior que zero")
    public Long idDono;

    @NotEmpty(message = "O restaurante deve ter pelo um endereço")
    public List<EnderecoRequest> enderecos = new ArrayList<>();

    @NotEmpty(message = "O restaurante deve ter pelo um cardápio")
    public List<CardapioRequest> cardapioRequests = new ArrayList<>();
}
