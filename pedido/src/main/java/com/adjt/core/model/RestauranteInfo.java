package com.adjt.core.model;

import java.io.Serializable;

public class RestauranteInfo implements Serializable {

    private Long id;
    private String nome;
    private String descricao;
    private String horarioFuncionamento;
    private Long idTipoCozinha;
    private Long idUsuario;
    private EnderecoRestauranteInfo endereco;
    private TipoCozinhaInfo tipoCozinha;

    public RestauranteInfo() {}

    public RestauranteInfo(long id, String nome, String descricao) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public String getHorarioFuncionamento() { return horarioFuncionamento; }
    public void setHorarioFuncionamento(String horarioFuncionamento) { this.horarioFuncionamento = horarioFuncionamento; }

    public Long getIdTipoCozinha() { return idTipoCozinha; }
    public void setIdTipoCozinha(Long idTipoCozinha) { this.idTipoCozinha = idTipoCozinha; }

    public Long getIdUsuario() { return idUsuario; }
    public void setIdUsuario(Long idUsuario) { this.idUsuario = idUsuario; }

    public EnderecoRestauranteInfo getEndereco() { return endereco; }
    public void setEndereco(EnderecoRestauranteInfo endereco) { this.endereco = endereco; }

    public TipoCozinhaInfo getTipoCozinha() { return tipoCozinha; }
    public void setTipoCozinha(TipoCozinhaInfo tipoCozinha) { this.tipoCozinha = tipoCozinha; }
}
