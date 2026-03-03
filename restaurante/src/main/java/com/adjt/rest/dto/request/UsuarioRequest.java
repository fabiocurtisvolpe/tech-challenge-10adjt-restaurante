package com.adjt.rest.dto.request;

import com.adjt.data.mapper.UsuarioSource;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;
import java.util.UUID;

public class UsuarioRequest implements UsuarioSource {

    @Positive(message = "O ID deve ser um número positivo maior que zero")
    public Long id;

    @NotBlank(message = "O nome não pode estar em branco")
    @Size(max = 50, message = "O nome deve ter no máximo 50 caracteres")
    public String nome;

    @NotBlank(message = "CPF é obrigatório")
    @Pattern(
            regexp = "^(\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}|\\d{11})$",
            message = "CPF deve estar no formato 000.000.000-00 ou apenas 11 números"
    )
    public String cpf;

    @NotBlank(message = "O e-mail não pode estar em branco")
    @Email(message = "O e-mail deve ser válido")
    @Size(max = 50, message = "O e-mail deve ter no máximo 50 caracteres")
    public String email;

    @NotBlank(message = "A senha não pode estar em branco")
    @Size(min = 6, message = "A senha deve ter no mínimo 6 caracteres")
    public String senha;

    public LocalDateTime dtCadastro;

    @NotNull(message = "O id perfil não pode estar vazio")
    @Positive(message = "O id perfil deve ser maior que zero")
    public Long idPerfil;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public String getNome() {
        return nome;
    }

    @Override
    public String getCpf() {
        return cpf;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public LocalDateTime getDtCadastro() {
        return dtCadastro;
    }

    @Override
    public UUID getKeycloakId() {
        return null;
    }

    @Override
    public Long getPerfilId() {
        return idPerfil;
    }
}
