package com.adjt.data.repository.jpa;

import com.adjt.data.entity.PedidoEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PedidoRepository implements PanacheRepositoryBase<PedidoEntity, Long> {
}
