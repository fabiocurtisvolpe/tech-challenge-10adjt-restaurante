package com.adjt.data.mapper;

import java.time.LocalDateTime;

public interface ClienteSource {
    Long getId();
    String getNome();
    String getCpf();
    String getEmail();
    String getSenha();
    LocalDateTime getDtCadastro();
}
