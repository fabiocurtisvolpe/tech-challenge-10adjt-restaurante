package com.adjt.data.repository.adapter;

import com.adjt.core.model.Pedido;
import com.adjt.core.port.PedidoPort;
import com.adjt.core.util.MensagemUtil;
import com.adjt.data.entity.PedidoEntity;
import com.adjt.data.mapper.PedidoMapper;
import com.adjt.data.repository.jpa.PedidoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.Objects;

@ApplicationScoped
public class PedidoRepositoryAdapter implements PedidoPort<Pedido> {

    private final PedidoRepository repository;
    private final PedidoMapper mapper;

    public PedidoRepositoryAdapter(PedidoRepository repository, PedidoMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Transactional
    @Override
    public Pedido criar(Pedido model) {
        PedidoEntity entity = this.mapper.toEntity(model);
        this.repository.persistAndFlush(entity);
        return this.mapper.toModel(entity);
    }

    @Transactional
    @Override
    public boolean cancelar(Long id) {
        return this.repository.deleteById(id);
    }

    @Transactional
    @Override
    public Pedido obterPorId(Long id) {
        PedidoEntity entity = this.repository.findById(id);
        if (entity != null) {
            return mapper.toModel(entity);
        }
        return null;
    }

    @Transactional
    @Override
    public Pedido atualizar(Long id, Integer statusCode) {
        PedidoEntity entity = this.repository.findById(id);
        Objects.requireNonNull(entity, MensagemUtil.NAO_FOI_POSSIVEL_EXECUTAR_OPERACAO);

        entity.statusCode = statusCode;
        return mapper.toModel(entity);
    }
}
