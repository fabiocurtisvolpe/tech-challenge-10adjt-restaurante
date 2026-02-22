package com.adjt.data.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import org.hibernate.envers.Audited;

@Entity
@Table(name = "tb_perfil")
@Audited
public class PerfilEntity extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(name = "nome", nullable = false, length = 20, unique = true)
    public String nome;
}
