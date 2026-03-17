package com.adjt.data.repository.adapter;

import com.adjt.base.core.exception.NotificacaoException;
import com.adjt.core.model.Cliente;
import com.adjt.core.model.Endereco;
import com.adjt.core.port.ClientePort;
import com.adjt.core.util.MensagemUtil;
import com.adjt.data.entity.ClienteEntity;
import com.adjt.data.entity.EnderecoEntity;
import com.adjt.data.mapper.ClienteMapper;
import com.adjt.data.mapper.EnderecoMapper;
import com.adjt.data.repository.jpa.ClienteRepository;
import com.adjt.base.rest.interceptor.UserContext;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@ApplicationScoped
public class ClienteRepositoryAdapter implements ClientePort<Cliente> {

    private final ClienteRepository repository;
    private final ClienteMapper mapper;
    private final EnderecoMapper enderecoMapper;

    private final UserContext userContext;

    public ClienteRepositoryAdapter(ClienteRepository repository,
                                    ClienteMapper mapper,
                                    EnderecoMapper enderecoMapper,
                                    UserContext userContext) {
        this.repository = repository;
        this.mapper = mapper;
        this.enderecoMapper = enderecoMapper;
        this.userContext = userContext;
    }

    @Transactional
    @Override
    public Cliente criar(Cliente model) {
        ClienteEntity entity = this.mapper.toEntity(model);
        this.repository.persistAndFlush(entity);
        return this.mapper.toModel(entity);
    }

    @Transactional
    @Override
    public Cliente atualizar(Cliente model) {

        ClienteEntity entity = repository.findById(model.getId());
        Objects.requireNonNull(entity, MensagemUtil.NAO_FOI_POSSIVEL_EXECUTAR_OPERACAO);

        this.validarUsuarioLogado(model.getId());

        entity.nome = model.getNome();
        entity.cpf = model.getCpf();
        entity.email = model.getEmail();

        atualizarEnderecos(entity, model.getEnderecos());

        return mapper.toModel(entity);
    }

    private void atualizarEnderecos(ClienteEntity clienteEntity, List<Endereco> enderecosModel) {

        if ((enderecosModel == null) || (enderecosModel.isEmpty())) return;

        clienteEntity.enderecos.removeIf(existing ->
                enderecosModel.stream().noneMatch(m -> m.getId() != null && m.getId().equals(existing.id))
        );

        for (Endereco model : enderecosModel) {
            if (model.getId() != null) {
                clienteEntity.enderecos.stream()
                        .filter(e -> e.id.equals(model.getId()))
                        .findFirst()
                        .ifPresent(existing -> enderecoMapper.updateEntityFromModel(model, existing));
            } else {
                EnderecoEntity novaEntity = enderecoMapper.toEntity(model);
                clienteEntity.addEndereco(novaEntity);
            }
        }
    }

    @Transactional
    @Override
    public Boolean excluir(Long id) {
        this.validarUsuarioLogado(id);
        return this.repository.deleteById(id);
    }

    @Transactional
    @Override
    public Cliente obterPorId(Long id) {
        ClienteEntity entity = this.repository.findById(id);
        if (entity != null) {
            this.validarUsuarioLogado(id);
            return mapper.toModel(entity);
        }
        return null;
    }

    @Transactional
    @Override
    public Cliente obterPorCpf(String cpf) {
        Optional<ClienteEntity> entity = this.repository.buscarPorCpf(cpf);
        entity.ifPresent(clienteEntity -> this.validarUsuarioLogado(clienteEntity.id));
        return entity.map(mapper::toModel).orElse(null);
    }

    @Transactional
    @Override
    public Cliente obterPorEmail(String email) {
        Optional<ClienteEntity> entity = this.repository.buscarPorEmail(email);
        entity.ifPresent(clienteEntity -> this.validarUsuarioLogado(clienteEntity.id));
        return entity.map(mapper::toModel).orElse(null);
    }

    private void validarUsuarioLogado(Long id) {
        ClienteEntity entityLogada = this.repository.buscarPorKeycloakId(userContext.getKeycloakId())
                .orElseThrow(() -> new NotificacaoException(MensagemUtil.CLIENTE_NAO_ENCONTRADO));

        if (!entityLogada.id.equals(id)) {
            throw new NotificacaoException(MensagemUtil.NAO_FOI_POSSIVEL_EXECUTAR_OPERACAO);
        }
    }
}
