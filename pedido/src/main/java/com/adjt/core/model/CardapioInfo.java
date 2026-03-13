package com.adjt.core.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class CardapioInfo implements Serializable {

    private Long id;
    private String nome;
    private String descricao;
    private BigDecimal preco;
    private String foto;
    private Boolean disponivel;
    private Long idRestaurante;

    public CardapioInfo() {}

    public CardapioInfo(long id, String nome, String descricao, String preco, String foto) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.preco = new BigDecimal(preco);
        this.foto = foto;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public BigDecimal getPreco() { return preco; }
    public void setPreco(BigDecimal preco) { this.preco = preco; }

    public String getFoto() { return foto; }
    public void setFoto(String foto) { this.foto = foto; }

    public Boolean getDisponivel() { return disponivel; }
    public void setDisponivel(Boolean disponivel) { this.disponivel = disponivel; }

    public Long getIdRestaurante() { return idRestaurante; }
    public void setIdRestaurante(Long idRestaurante) { this.idRestaurante = idRestaurante; }
}
