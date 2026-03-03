package com.adjt.rest.interceptor;

import jakarta.enterprise.context.RequestScoped;

import java.util.UUID;

@RequestScoped
public class UserContext {
    private String email;
    private UUID keycloakId;
    private String nome;

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public UUID getKeycloakId() { return keycloakId; }
    public void setKeycloakId(UUID keycloakId) { this.keycloakId = keycloakId; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
}