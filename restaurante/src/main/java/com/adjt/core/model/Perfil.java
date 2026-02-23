package com.adjt.core.model;

import java.io.Serializable;

public class Perfil implements Serializable {

    protected Long id;
    protected String nome;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
