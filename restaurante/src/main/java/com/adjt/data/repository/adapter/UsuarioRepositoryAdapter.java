package com.adjt.data.repository.adapter;

import com.adjt.core.exception.NotificacaoException;
import com.adjt.core.model.Usuario;
import com.adjt.core.port.UsuarioPort;
import com.adjt.core.util.MensagemUtil;
import com.adjt.data.entity.UsuarioEntity;
import com.adjt.data.mapper.UsuarioMapper;
import com.adjt.data.repository.jpa.UsuarioRepository;
import com.adjt.rest.interceptor.UserContext;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.Objects;
import java.util.Optional;

@ApplicationScoped
public class UsuarioRepositoryAdapter implements UsuarioPort<Usuario> {

    private final UsuarioRepository repository;
    private final UsuarioMapper mapper;

    private final UserContext userContext;

    public UsuarioRepositoryAdapter(UsuarioRepository repository,
                                    UsuarioMapper mapper,
                                    UserContext userContext) {
        this.repository = repository;
        this.mapper = mapper;
        this.userContext = userContext;
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

        this.validarUsuarioLogado(model.getId());

        entity.nome = model.getNome();
        entity.cpf = model.getCpf();
        entity.email = model.getEmail();

        return mapper.toModel(entity);
    }

    @Transactional
    @Override
    public Boolean excluir(Long id) {
        this.validarUsuarioLogado(id);
        return this.repository.deleteById(id);
    }

    @Transactional
    @Override
    public Usuario obterPorId(Long id) {
        UsuarioEntity entity = this.repository.findById(id);
        if (entity != null) {
            this.validarUsuarioLogado(id);
            return mapper.toModel(entity);
        }
        return null;
    }

    @Transactional
    @Override
    public Usuario obterPorCpf(String cpf) {
        Optional<UsuarioEntity> entity = this.repository.buscarPorCpf(cpf);
        entity.ifPresent(clienteEntity -> this.validarUsuarioLogado(clienteEntity.id));
        return entity.map(mapper::toModel).orElse(null);
    }

    @Transactional
    @Override
    public Usuario obterPorEmail(String email) {
        Optional<UsuarioEntity> entity = this.repository.buscarPorEmail(email);
        entity.ifPresent(clienteEntity -> this.validarUsuarioLogado(clienteEntity.id));
        return entity.map(mapper::toModel).orElse(null);
    }

    private void validarUsuarioLogado(Long id) {
        UsuarioEntity entityLogada = this.repository.buscarPorKeycloakId(userContext.getKeycloakId())
                .orElseThrow(() -> new NotificacaoException(MensagemUtil.USUARIO_NAO_ENCONTRADO));

        if (!entityLogada.id.equals(id)) {
            throw new NotificacaoException(MensagemUtil.NAO_FOI_POSSIVEL_EXECUTAR_OPERACAO);
        }
    }
}
