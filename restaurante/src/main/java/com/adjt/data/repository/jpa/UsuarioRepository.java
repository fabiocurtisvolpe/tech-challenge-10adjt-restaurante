package com.adjt.data.repository.jpa;

import com.adjt.data.entity.UsuarioEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Optional;

@ApplicationScoped
public class UsuarioRepository implements PanacheRepositoryBase<UsuarioEntity, Long> {

    public Optional<UsuarioEntity> buscarPorCpf(String cpf) {
        return find("cpf", cpf).firstResultOptional();
    }

    public Optional<UsuarioEntity> buscarPorEmail(String email) {
        return find("email", email).firstResultOptional();
    }
}