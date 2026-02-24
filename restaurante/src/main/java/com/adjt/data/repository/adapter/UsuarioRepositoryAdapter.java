package com.adjt.data.repository.adapter;

import com.adjt.core.model.Usuario;
import com.adjt.core.port.UsuarioPort;
import com.adjt.core.util.MensagemUtil;
import com.adjt.data.entity.UsuarioEntity;
import com.adjt.data.mapper.UsuarioMapper;
import com.adjt.data.repository.jpa.UsuarioRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.Objects;
import java.util.Optional;

@ApplicationScoped
public class UsuarioRepositoryAdapter implements UsuarioPort<Usuario> {

    private final UsuarioRepository repository;
    private final UsuarioMapper mapper;

    public UsuarioRepositoryAdapter(UsuarioRepository repository,
                                    UsuarioMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Transactional
    @Override
    public Usuario criar(Usuario model) {
        UsuarioEntity entity = this.mapper.toEntity(model);
        this.repository.persistAndFlush(entity);
        return this.mapper.toModel(entity);
    }

    @Transactional
    @Override
    public Usuario atualizar(Usuario model) {

        UsuarioEntity entity = repository.findById(model.getId());
        Objects.requireNonNull(entity, MensagemUtil.NAO_FOI_POSSIVEL_EXECUTAR_OPERACAO);

        entity.nome = model.getNome();
        entity.cpf = model.getCpf();
        entity.email = model.getEmail();
        entity.senha = model.getSenha();

        return mapper.toModel(entity);
    }

    @Transactional
    @Override
    public Boolean excluir(Long id) {
        return this.repository.deleteById(id);
    }

    @Transactional
    @Override
    public Usuario obterPorId(Long id) {
        UsuarioEntity entity = this.repository.findById(id);
        if (entity != null) return mapper.toModel(entity);
        return null;
    }

    @Transactional
    @Override
    public Usuario obterPorCpf(String cpf) {
        Optional<UsuarioEntity> entity = this.repository.buscarPorCpf(cpf);
        return entity.map(mapper::toModel).orElse(null);
    }

    @Transactional
    @Override
    public Usuario obterPorEmail(String email) {
        Optional<UsuarioEntity> entity = this.repository.buscarPorEmail(email);
        return entity.map(mapper::toModel).orElse(null);
    }
}
