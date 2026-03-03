package com.adjt.data.entity;

import com.adjt.data.mapper.UsuarioSource;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.envers.Audited;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "tb_usuario", uniqueConstraints = {
        @UniqueConstraint(name = "uk_usuario_cpf_email", columnNames = {"cpf", "email"})
})
@Audited
public class UsuarioEntity extends PanacheEntityBase implements UsuarioSource {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @NotBlank
    @Size(max = 50)
    @Column(length = 50, nullable = false)
    public String nome;

    @NotBlank
    @Size(max = 20)
    @Column(length = 20, nullable = false)
    public String cpf;

    @Email
    @NotBlank
    @Size(max = 50)
    @Column(length = 50, nullable = false)
    public String email;

    @CreationTimestamp
    @Column(name = "dt_cadastro", updatable = false, columnDefinition = "TIMESTAMP(6)")
    public LocalDateTime dtCadastro;

    @ManyToOne
    @JoinColumn(name = "id_perfil", foreignKey = @ForeignKey(name = "fk_usuario_perfil"))
    public PerfilEntity perfil;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    public List<RestauranteEntity> restaurantes = new ArrayList<>();

    @Column(name = "keycloak_id", unique = true)
    public UUID keycloakId;

    public void addRestaurante(RestauranteEntity restaurante) {
        restaurante.usuario = this;
        this.restaurantes.add(restaurante);
    }

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
        return keycloakId;
    }

    @Override
    public Long getPerfilId() {
        return perfil.id;
    }
}
