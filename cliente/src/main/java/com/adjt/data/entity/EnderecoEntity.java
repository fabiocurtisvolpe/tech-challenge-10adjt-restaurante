package com.adjt.data.entity;

import com.adjt.data.mapper.EnderecoSource;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.envers.Audited;

@Entity
@Table(name = "tb_endereco")
@Audited
public class EnderecoEntity extends PanacheEntityBase implements EnderecoSource {

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cliente", nullable = false, foreignKey = @ForeignKey(name = "fk_endereco_cliente"))
    public ClienteEntity cliente;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public String getRua() {
        return rua;
    }

    @Override
    public String getBairro() {
        return bairro;
    }

    @Override
    public String getCep() {
        return cep;
    }

    @Override
    public String getComplemento() {
        return complemento;
    }

    @Override
    public String getNumero() {
        return numero;
    }

    @Override
    public String getCidade() {
        return cidade;
    }

    @Override
    public String getUf() {
        return uf;
    }

    @Override
    public Boolean getPrincipal() {
        return principal;
    }

    @Override
    public String getObservacao() {
        return observacao;
    }
}
