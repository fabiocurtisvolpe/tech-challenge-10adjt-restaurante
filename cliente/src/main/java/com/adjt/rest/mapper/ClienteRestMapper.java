package com.adjt.rest.mapper;

import com.adjt.core.model.Cliente;
import com.adjt.rest.dto.request.ClienteRequest;
import com.adjt.rest.dto.response.ClienteResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "cdi", uses = {EnderecoRestMapper.class})
public interface ClienteRestMapper {

    Cliente toModel(ClienteRequest request);

    @Mapping(target = "id", source = "id")
    ClienteRequest toRequest(Cliente model);

    @Mapping(target = "id", source = "id")
    ClienteResponse toResponse(Cliente model);
}
