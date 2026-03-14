package com.adjt.data.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import org.hibernate.envers.Audited;

import java.math.BigDecimal;

@Entity
@Table(name = "tb_item_pedido")
@Audited
public class ItemPedidoEntity extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_pedido", nullable = false)
    public PedidoEntity pedido;

    @Column(name = "id_cardapio", nullable = false)
    public Long idCardapio;

    @Column(nullable = false)
    public Integer qtd;

    @Column(nullable = false, precision = 13, scale = 2)
    public BigDecimal valor;
}
