package com.adjt.data.mapper;

import com.adjt.core.model.Cliente;
import com.adjt.core.model.Endereco;
import com.adjt.data.entity.ClienteEntity;
import com.adjt.data.entity.EnderecoEntity;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class ClienteMapper {

    @Inject
    EnderecoMapper enderecoMapper;

    @Inject
    PerfilMapper perfilMapper;

    public Cliente toModel(ClienteEntity entity) {
        if (entity == null) {
            return null;
        }

        Cliente cliente = new Cliente();
        cliente.setId(entity.id);
        cliente.setNome(entity.nome);
        cliente.setCpf(entity.cpf);
        cliente.setEmail(entity.email);
        cliente.setSenha(entity.senha);
        cliente.setDtCadastro(entity.dtCadastro);

        if (entity.enderecos != null) {
            List<Endereco> enderecos = entity.enderecos.stream()
                    .map(enderecoMapper::toModel)
                    .collect(Collectors.toList());
            cliente.setEnderecos(enderecos);
        }

        if (entity.perfil != null) {
            cliente.setPerfil(perfilMapper.toModel(entity.perfil));
        }

        return cliente;
    }

    public ClienteEntity toEntity(Cliente model) {
        if (model == null) {
            return null;
        }

        ClienteEntity entity = new ClienteEntity();
        entity.id = model.getId();
        entity.nome = model.getNome();
        entity.cpf = model.getCpf();
        entity.email = model.getEmail();
        entity.senha = model.getSenha();
        entity.dtCadastro = model.getDtCadastro();

        if (model.getEnderecos() != null) {
            List<EnderecoEntity> enderecos = model.getEnderecos().stream()
                    .map(enderecoMapper::toEntity)
                    .collect(Collectors.toList());
            entity.enderecos = enderecos;
        }

        if (model.getPerfil() != null) {
            entity.perfil = perfilMapper.toEntity(model.getPerfil());
        }

        return entity;
    }
}