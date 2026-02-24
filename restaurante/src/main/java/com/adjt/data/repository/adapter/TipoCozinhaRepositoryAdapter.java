package com.adjt.data.repository.adapter;

import com.adjt.core.model.TipoCozinha;
import com.adjt.core.port.TipoCozinhaPort;
import com.adjt.data.entity.TipoCozinhaEntity;
import com.adjt.data.mapper.TipoCozinhaMapper;
import com.adjt.data.repository.jpa.TipoCozinhaRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class TipoCozinhaRepositoryAdapter implements TipoCozinhaPort<TipoCozinha> {

    private final TipoCozinhaRepository repository;
    private final TipoCozinhaMapper mapper;

    public TipoCozinhaRepositoryAdapter(TipoCozinhaRepository repository,
                                        TipoCozinhaMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Transactional
    @Override
    public TipoCozinha obterPorId(Long id) {
        TipoCozinhaEntity entity = this.repository.findById(id);
        return mapper.toModel(entity);
    }
}
