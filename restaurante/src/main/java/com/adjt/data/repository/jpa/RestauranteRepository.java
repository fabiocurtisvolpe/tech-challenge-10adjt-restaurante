package com.adjt.data.repository.jpa;

import com.adjt.data.entity.RestauranteEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class RestauranteRepository implements PanacheRepositoryBase<RestauranteEntity, Long> {
}