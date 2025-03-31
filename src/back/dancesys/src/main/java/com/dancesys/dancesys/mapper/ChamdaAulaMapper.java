package com.dancesys.dancesys.mapper;

import com.dancesys.dancesys.dto.ChamadaAulaDto;
import com.dancesys.dancesys.entity.ChamadaAula;

public class ChamdaAulaMapper {
    public static ChamadaAulaDto toDto(ChamadaAula ca){
        if(ca == null) return null;

        ChamadaAulaDto dto = new ChamadaAulaDto();

        dto.setId(ca.getId());
        dto.setPresenca(ca.getPresenca());
        dto.setIdAulaOcorrencia(ca.getIdAulaOcorrencia());
        dto.setIdAluno(ca.getIdAluno());

        return dto;
    }

    public static ChamadaAula toEntity(ChamadaAulaDto ca){
        if(ca == null) return null;

        ChamadaAula entity = new ChamadaAula();

        entity.setId(ca.getId());
        entity.setPresenca(ca.getPresenca());
        entity.setIdAulaOcorrencia(ca.getIdAulaOcorrencia());
        entity.setIdAluno(ca.getIdAluno());

        return entity;
    }
}
