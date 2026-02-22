package com.adjt.data.entity;

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

@Entity
@Table(name = "tb_cliente")
@Audited
public class ClienteEntity extends PanacheEntityBase {

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

    @NotBlank
    @Size(max = 255)
    @Column(length = 255, nullable = false)
    public String senha;

    @CreationTimestamp // Preenche automaticamente a data de cadastro
    @Column(name = "dt_cadastro", updatable = false)
    public LocalDateTime dtCadastro;

    // Relacionamento 1:N com Endereços
    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    public List<EnderecoEntity> enderecos = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "id_perfil")
    public PerfilEntity perfil;

    public void addEndereco(EnderecoEntity endereco) {
        endereco.cliente = this;
        this.enderecos.add(endereco);
    }

    @PrePersist
    protected void onCreate() {
        if (this.dtCadastro == null) {
            this.dtCadastro = LocalDateTime.now();
        }
    }
}
