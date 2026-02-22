package com.adjt.data.repository.adapter;

import com.adjt.core.model.Cliente;
import com.adjt.core.model.Endereco;
import com.adjt.core.port.ClientePort;
import com.adjt.core.util.MensagemUtil;
import com.adjt.data.entity.ClienteEntity;
import com.adjt.data.entity.EnderecoEntity;
import com.adjt.data.mapper.ClienteMapper;
import com.adjt.data.mapper.EnderecoMapper;
import com.adjt.data.repository.jpa.ClienteRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Objects;

@ApplicationScoped
public class ClienteRepositoryAdapter implements ClientePort<Cliente> {

    private final ClienteRepository repository;
    private final ClienteMapper mapper;
    private final EnderecoMapper enderecoMapper;

    public ClienteRepositoryAdapter(ClienteRepository repository,
                                    ClienteMapper mapper,
                                    EnderecoMapper enderecoMapper) {
        this.repository = repository;
        this.mapper = mapper;
        this.enderecoMapper = enderecoMapper;
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

        entity.nome = model.getNome();
        entity.cpf = model.getCpf();
        entity.email = model.getEmail();
        entity.senha = model.getSenha();

        atualizarEnderecos(entity, model.getEnderecos());

        return mapper.toModel(entity);
    }

    private void atualizarEnderecos(ClienteEntity clienteEntity, List<Endereco> enderecosModel) {

        if (enderecosModel == null) return;

        clienteEntity.enderecos.removeIf(existing ->
                enderecosModel.stream().noneMatch(m -> m.getId() != null && m.getId().equals(existing.id))
        );

        for (Endereco model : enderecosModel) {
            if (model.getId() != null) {
                clienteEntity.enderecos.stream()
                        .filter(e -> e.id.equals(model.getId()))
                        .findFirst()
                        .ifPresent(existing -> {
                            existing.rua = model.getRua();
                            existing.bairro = model.getBairro();
                            existing.cep = model.getCep();
                            existing.complemento = model.getComplemento();
                            existing.numero = model.getNumero();
                            existing.cidade = model.getCidade();
                            existing.uf = model.getUf();
                            existing.principal = model.getPrincipal();
                            existing.observacao = model.getObservacao();
                        });
            } else {
                EnderecoEntity novaEntity = enderecoMapper.toEntity(model);
                clienteEntity.addEndereco(novaEntity);
            }
        }
    }

    @Override
    public Boolean excluir(Long id) {

        return null;
    }

    @Override
    public Cliente obterPorId(Long id) {
        return null;
    }

    @Override
    public Cliente obterPorCpf(String cpf) {
        return null;
    }

    @Override
    public Cliente obterPorEmail(String email) {
        return null;
    }
}
