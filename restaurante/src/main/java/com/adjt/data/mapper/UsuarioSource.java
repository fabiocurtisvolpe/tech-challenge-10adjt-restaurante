package com.adjt.data.mapper;

import java.time.LocalDateTime;
import java.util.UUID;

public interface UsuarioSource {
    Long getId();
    String getNome();
    String getCpf();
    String getEmail();
    LocalDateTime getDtCadastro();
    UUID getKeycloakId();
    Long getPerfilId();
}
