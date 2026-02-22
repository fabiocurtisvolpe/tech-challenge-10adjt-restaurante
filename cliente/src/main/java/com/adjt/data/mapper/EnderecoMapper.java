package com.adjt.data.mapper;

import com.adjt.core.model.Endereco;
import com.adjt.data.entity.EnderecoEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "cdi")
public interface EnderecoMapper {

    Endereco toModel(EnderecoEntity entity);

    @Mapping(target = "cliente", ignore = true)
    EnderecoEntity toEntity(Endereco model);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "cliente", ignore = true)
    void updateEntityFromModel(Endereco model, @MappingTarget EnderecoEntity entity);
}
