package com.dancesys.dancesys.mapper;

import com.dancesys.dancesys.dto.AulaOcorrenciaDto;
import com.dancesys.dancesys.entity.AulaOcorrencia;

public class AulaOcorrenciaMapper {

    public static AulaOcorrenciaDto toDto(AulaOcorrencia ao){
        AulaOcorrenciaDto dto = new AulaOcorrenciaDto();

        dto.setId(ao.getId());
        dto.setCodigoAula(ao.getCodigoAula());
        dto.setStatus(ao.getStatus());
        dto.setData(ao.getData());
        dto.setIdAula(ao.getIdAula());
        dto.setMotivoCancelamento(ao.getMotivoCancelamento());
        dto.setListaIdsAlunos(ao.getListaIdsAlunos());

        return dto;
    }

    public static AulaOcorrencia toEntity(AulaOcorrenciaDto ao){
        AulaOcorrencia entity = new AulaOcorrencia();

        entity.setId(ao.getId());
        entity.setCodigoAula(ao.getCodigoAula());
        entity.setStatus(ao.getStatus());
        entity.setData(ao.getData());
        entity.setIdAula(ao.getIdAula());
        entity.setMotivoCancelamento(ao.getMotivoCancelamento());
        entity.setListaIdsAlunos(ao.getListaIdsAlunos());

        return entity;
    }
}
