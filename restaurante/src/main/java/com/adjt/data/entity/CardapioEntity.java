package com.adjt.data.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.envers.Audited;

import java.math.BigDecimal;

@Entity
@Table(name = "tb_cardapio")
@Audited
public class CardapioEntity extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @NotBlank
    @Size(max = 50)
    @Column(length = 50, nullable = false)
    public String nome;

    @Size(max = 1000)
    @Column(length = 1000)
    public String descricao;

    @NotNull
    @Column(nullable = false, precision = 19, scale = 2)
    public BigDecimal preco;

    @Size(max = 2000)
    @Column(length = 2000)
    public String foto;

    @NotNull
    @Column(nullable = false)
    public Boolean disponivel;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "id_restaurante", nullable = false)
    public RestauranteEntity restaurante;
}
