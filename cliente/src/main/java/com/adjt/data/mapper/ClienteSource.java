package com.adjt.data.mapper;

import java.time.LocalDateTime;
import java.util.UUID;

public interface ClienteSource {
    Long getId();
    String getNome();
    String getCpf();
    String getEmail();
    String getSenha();
    LocalDateTime getDtCadastro();
    UUID getKeycloakId();
}
