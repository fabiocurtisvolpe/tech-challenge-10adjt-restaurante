package com.adjt.data.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.envers.Audited;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tb_restaurante")
@Audited
public class RestauranteEntity extends PanacheEntityBase {

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

    @NotBlank
    @Size(max = 500)
    @Column(name = "horario_funcionamento", length = 500, nullable = false)
    public String horarioFuncionamento;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "id_tipo_cozinha", nullable = false)
    public TipoCozinhaEntity tipoCozinha;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    public UsuarioEntity usuario;

    @OneToMany(mappedBy = "restaurante", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    public List<EnderecoEntity> enderecos = new ArrayList<>();

    @OneToMany(mappedBy = "restaurante", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    public List<CardapioEntity> cardapios = new ArrayList<>();

    public void addEndereco(EnderecoEntity endereco) {
        endereco.restaurante = this;
        this.enderecos.add(endereco);
    }

    public void addCardapio(CardapioEntity cardapio) {
        cardapio.restaurante = this;
        this.cardapios.add(cardapio);
    }
}
