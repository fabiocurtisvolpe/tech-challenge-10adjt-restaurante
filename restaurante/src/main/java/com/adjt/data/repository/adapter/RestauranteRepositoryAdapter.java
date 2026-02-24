package com.adjt.data.repository.adapter;

import com.adjt.core.model.Restaurante;
import com.adjt.core.port.RestaurantePort;
import com.adjt.data.entity.RestauranteEntity;
import com.adjt.data.mapper.RestauranteMapper;
import com.adjt.data.repository.jpa.RestauranteRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class RestauranteRepositoryAdapter implements RestaurantePort<Restaurante> {

    private final RestauranteRepository repository;
    private final RestauranteMapper mapper;

    public RestauranteRepositoryAdapter(RestauranteRepository repository, RestauranteMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Transactional
    @Override
    public Restaurante criar(Restaurante model) {
        return null;
    }

    @Transactional
    @Override
    public Restaurante atualizar(Restaurante model) {
        return null;
    }

    @Transactional
    @Override
    public Boolean excluir(Long id) {
        return this.repository.deleteById(id);
    }

    @Transactional
    @Override
    public Restaurante obterPorId(Long id) {
        RestauranteEntity entity = this.repository.findById(id);
        return mapper.toModel(entity);
    }
}
