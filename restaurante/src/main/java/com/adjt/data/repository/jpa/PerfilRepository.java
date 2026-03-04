package com.adjt.data.repository.jpa;

import com.adjt.data.entity.PerfilEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Optional;

@ApplicationScoped
public class PerfilRepository implements PanacheRepositoryBase<PerfilEntity, Long> {

    public Optional<PerfilEntity> buscarPorNome(String nome) {
        return find("nome", nome).firstResultOptional();
    }
    public Optional<PerfilEntity> buscarPorId(Long id) { return find("id", id).firstResultOptional();}
}
