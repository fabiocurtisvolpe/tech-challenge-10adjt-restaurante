package com.adjt.data.repository.adapter;

import com.adjt.core.model.Cardapio;
import com.adjt.core.model.CardapioPaginado;
import com.adjt.core.port.CardapioPort;
import com.adjt.core.util.MensagemUtil;
import com.adjt.data.entity.CardapioEntity;
import com.adjt.data.mapper.CardapioMapper;
import com.adjt.data.repository.jpa.CardapioRepository;
import io.quarkus.panache.common.Parameters;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Objects;

@ApplicationScoped
public class CardapioRepositoryAdapter implements CardapioPort<Cardapio> {

    private final CardapioRepository repository;
    private final CardapioMapper mapper;

    public CardapioRepositoryAdapter(CardapioRepository repository, CardapioMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Transactional
    @Override
    public Cardapio criar(Cardapio model) {

        CardapioEntity entity = this.mapper.toEntity(model);
        this.repository.persistAndFlush(entity);
        return this.mapper.toModel(entity);
    }

    @Transactional
    @Override
    public Cardapio atualizar(Cardapio model) {

        CardapioEntity entity = repository.findById(model.getId());
        Objects.requireNonNull(entity, MensagemUtil.NAO_FOI_POSSIVEL_EXECUTAR_OPERACAO);

        entity.nome = model.getNome();
        entity.descricao = model.getDescricao();
        entity.foto = model.getFoto();
        entity.disponivel = model.getDisponivel();

        return mapper.toModel(entity);
    }

    @Transactional
    @Override
    public Boolean excluir(Long id) {
        return this.repository.deleteById(id);
    }

    @Transactional
    @Override
    public Cardapio obterPorId(Long id) {
        CardapioEntity entity = this.repository.findById(id);
        if (entity != null) return mapper.toModel(entity);
        return null;
    }

    @Override
    public CardapioPaginado listarPorRestaurante(Long idRestaurante, int page, int size, Boolean disponivel) {
        StringBuilder query = new StringBuilder("restaurante.id = :idRestaurante");
        Parameters params = Parameters.with("idRestaurante", idRestaurante);

        if (disponivel != null) {
            query.append(" and disponivel = :disponivel");
            params.and("disponivel", disponivel);
        }

        var panacheQuery = this.repository.find(query.toString(), params);
        long total = panacheQuery.count();

        List<Cardapio> lista = panacheQuery.page(page, size).list()
                .stream().map(mapper::toModel).toList();

        return new CardapioPaginado(lista, total);
    }
}
