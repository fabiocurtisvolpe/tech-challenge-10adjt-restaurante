package com.adjt.data.repository.jpa;

import com.adjt.data.entity.ClienteEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Optional;

@ApplicationScoped
public class ClienteRepository implements PanacheRepositoryBase<ClienteEntity, Long> {

    public Optional<ClienteEntity> buscarPorCpf(String cpf) {
        return find("cpf", cpf).firstResultOptional();
    }

    public Optional<ClienteEntity> buscarPorEmail(String email) {
        return find("email", email).firstResultOptional();
    }
}