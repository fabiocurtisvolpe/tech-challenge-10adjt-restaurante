package com.adjt.rest.dto.response;

import java.util.ArrayList;
import java.util.List;

public class RestauranteResponse {

    public Long id;
    public String nome;
    public String descricao;
    public String horarioFuncionamento;
    public TipoCozinhaResponse tipoCozinha;
    public Long idDono;
    public List<EnderecoResponse> enderecos = new ArrayList<>();
    public List<CardapioResponse> cardapios = new ArrayList<>();
}
