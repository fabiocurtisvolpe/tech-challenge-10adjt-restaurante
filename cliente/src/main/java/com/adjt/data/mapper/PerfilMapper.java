package com.adjt.data.mapper;

import com.adjt.core.model.Perfil;
import com.adjt.data.entity.PerfilEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "cdi")
public interface PerfilMapper {

    Perfil toModel(PerfilEntity entity);

    @Mapping(target = "id", source = "id")
    PerfilEntity toEntity(Perfil model);
}
