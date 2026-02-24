package com.adjt.data.repository.jpa;

import com.adjt.data.entity.TipoCozinhaEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class TipoCozinhaRepository implements PanacheRepositoryBase<TipoCozinhaEntity, Long> {
}