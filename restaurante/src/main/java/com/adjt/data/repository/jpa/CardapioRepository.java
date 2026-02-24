package com.adjt.data.repository.jpa;

import com.adjt.data.entity.CardapioEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Optional;

@ApplicationScoped
public class CardapioRepository implements PanacheRepositoryBase<CardapioEntity, Long> {

    public Optional<CardapioEntity> buscarPorRestaurante(Long id) {
        return find("restaurante.id", id).firstResultOptional();
    }
}