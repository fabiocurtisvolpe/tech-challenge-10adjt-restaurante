package com.adjt.rest.dto.response;

import com.adjt.rest.dto.DiaFuncionamento;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RestauranteResponse {

    public Long id;
    public String nome;
    public String descricao;
    protected Map<String, DiaFuncionamento> horarioFuncionamento;
    public Long idTipoCozinha;
    public Long idDono;
    public List<EnderecoResponse> enderecos = new ArrayList<>();
    public List<CardapioResponse> cardapioRequests = new ArrayList<>();
}
