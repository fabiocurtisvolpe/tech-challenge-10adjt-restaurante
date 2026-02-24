package com.adjt.data.entity;

import com.adjt.data.mapper.TipoCozinhaSource;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.envers.Audited;

@Entity
@Table(name = "tb_tipo_cozinha")
@Audited
public class TipoCozinhaEntity extends PanacheEntityBase implements TipoCozinhaSource {

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


    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public String getNome() {
        return this.nome;
    }

    @Override
    public String getDescricao() {
        return this.descricao;
    }
}
