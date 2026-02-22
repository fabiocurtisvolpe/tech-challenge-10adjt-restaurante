package com.adjt.data.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.envers.Audited;

@Entity
@Table(name = "tb_endereco")
@Audited
public class EnderecoEntity extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @NotBlank
    @Size(max = 100)
    @Column(length = 100, nullable = false)
    public String rua;

    @NotBlank
    @Size(max = 50)
    @Column(length = 50, nullable = false)
    public String bairro;

    @NotBlank
    @Size(max = 10)
    @Column(length = 10, nullable = false)
    public String cep;

    @Size(max = 50)
    @Column(length = 50)
    public String complemento;

    @Size(max = 10)
    @Column(length = 10)
    public String numero;

    @NotBlank
    @Size(max = 50)
    @Column(length = 50, nullable = false)
    public String cidade;

    @NotBlank
    @Size(min = 2, max = 2)
    @Column(length = 2, nullable = false)
    public String uf;

    @Column(nullable = false)
    public Boolean principal = false;

    @Column(columnDefinition = "TEXT")
    public String observacao;

    // Relacionamento com Cliente
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cliente", nullable = false)
    public ClienteEntity cliente;
}
