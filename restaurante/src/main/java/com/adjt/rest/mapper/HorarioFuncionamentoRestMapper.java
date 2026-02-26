package com.adjt.rest.mapper;

import com.adjt.rest.dto.DiaFuncionamento;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

public class HorarioFuncionamentoRestMapper {

    public static String converterParaJson(Map<String, DiaFuncionamento> mapa) {
        try {
            final ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(mapa);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Erro ao converter mapa de horários para JSON", e);
        }
    }
}
