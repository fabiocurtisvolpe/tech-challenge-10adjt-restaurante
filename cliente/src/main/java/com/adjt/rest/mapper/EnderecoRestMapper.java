package com.adjt.rest.mapper;

import com.adjt.core.model.Endereco;
import com.adjt.rest.dto.request.EnderecoRequest;
import com.adjt.rest.dto.response.EnderecoResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "cdi")
public interface EnderecoRestMapper {

    Endereco toModel(EnderecoRequest request);

    @Mapping(target = "cliente", ignore = true)
    EnderecoRequest toRequest(Endereco model);

    @Mapping(target = "cliente", ignore = true)
    EnderecoResponse toResponse(Endereco model);
}
