package com.adjt.data.entity;

import com.adjt.data.mapper.ClienteSource;
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
@Table(name = "tb_cliente", uniqueConstraints = {
        @UniqueConstraint(name = "uk_cliente_cpf_email", columnNames = {"cpf", "email"})
})
@Audited
public class ClienteEntity extends PanacheEntityBase implements ClienteSource {

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
    @Column(nullable = false)
    public String senha;

    @CreationTimestamp
    @Column(name = "dt_cadastro", updatable = false, columnDefinition = "TIMESTAMP(6)")
    public LocalDateTime dtCadastro;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    public List<EnderecoEntity> enderecos = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "id_perfil", foreignKey = @ForeignKey(name = "fk_cliente_perfil"))
    public PerfilEntity perfil;

    public void addEndereco(EnderecoEntity endereco) {
        endereco.cliente = this;
        this.enderecos.add(endereco);
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
    public String getSenha() {
        return senha;
    }

    @Override
    public LocalDateTime getDtCadastro() {
        return dtCadastro;
    }
}
