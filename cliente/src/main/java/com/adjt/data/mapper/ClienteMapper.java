package com.adjt.data.mapper;

import com.adjt.core.model.Cliente;
import com.adjt.data.entity.ClienteEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "cdi", uses = {EnderecoMapper.class})
public interface ClienteMapper {

    Cliente toModel(ClienteEntity entity);

    @Mapping(target = "id", source = "id")
    ClienteEntity toEntity(Cliente model);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dtCadastro", ignore = true)
    void updateEntityFromModel(Cliente model, @MappingTarget ClienteEntity entity);
}