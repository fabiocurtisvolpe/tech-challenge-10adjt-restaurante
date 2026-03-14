package com.adjt.data.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.envers.Audited;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tb_pedido")
@Audited
public class PedidoEntity extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @CreationTimestamp
    @Column(name = "dt_cadastro", updatable = false)
    public LocalDateTime dtCadastro;

    @Column(name = "id_cliente", nullable = false)
    public Long idCliente;

    @Column(name = "id_restaurante", nullable = false)
    public Long idRestaurante;

    @Column(name = "valor_total", nullable = false, precision = 13, scale = 2)
    public BigDecimal valorTotal;

    @Column(name = "status_code")
    public Integer statusCode;

    @Column(name = "response_code")
    public Integer responseCode;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    public List<ItemPedidoEntity> itens = new ArrayList<>();

    public void addItem(ItemPedidoEntity item) {
        item.pedido = this;
        this.itens.add(item);
    }
}
