package com.adjt.data.mapper;

import com.adjt.core.model.Cliente;
import com.adjt.core.model.Endereco;
import com.adjt.data.entity.ClienteEntity;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class ClienteMapper {

    private final EnderecoMapper enderecoMapper;
    private final PerfilMapper perfilMapper;

    public ClienteMapper(EnderecoMapper enderecoMapper, PerfilMapper perfilMapper) {
        this.enderecoMapper = enderecoMapper;
        this.perfilMapper = perfilMapper;
    }

    public Cliente toModel(ClienteSource source) {
        if (source == null) {
            return null;
        }

        Cliente cliente = new Cliente();
        cliente.setId(source.getId());
        cliente.setNome(source.getNome());
        cliente.setCpf(source.getCpf());
        cliente.setEmail(source.getEmail());
        cliente.setSenha(source.getSenha());
        cliente.setDtCadastro(source.getDtCadastro());
        cliente.setKeycloakId(source.getKeycloakId());

        if (source instanceof ClienteEntity entity) {

            if (entity.enderecos != null) {
                List<Endereco> enderecos = entity.enderecos.stream()
                        .map(enderecoMapper::toModel)
                        .collect(Collectors.toList());
                cliente.setEnderecos(enderecos);
            }

            if (entity.perfil != null) {
                cliente.setPerfil(perfilMapper.toModel(entity.perfil));
            }
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
        entity.keycloakId = model.getKeycloakId();

        if (model.getEnderecos() != null) {
            model.getEnderecos().stream()
                    .map(enderecoMapper::toEntity)
                    .forEach(entity::addEndereco);
        }

        if (model.getPerfil() != null) {
            entity.perfil = perfilMapper.toEntity(model.getPerfil());
        }

        return entity;
    }
}