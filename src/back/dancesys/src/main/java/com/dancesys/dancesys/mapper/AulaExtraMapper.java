package com.dancesys.dancesys.mapper;

import com.dancesys.dancesys.dto.AulaExtraDTO;
import com.dancesys.dancesys.entity.AulaExtra;

public class AulaExtraMapper {
    public static AulaExtra toEntity(AulaExtraDTO dto) {
        if (dto == null) {
            return null;
        }

        AulaExtra entity = new AulaExtra();
        entity.setId(dto.getId());
        entity.setHorarioInicio(dto.getHorarioInicio());
        entity.setHorarioFim(dto.getHorarioFim());
        entity.setSituacao(dto.getSituacao());
        entity.setMotivo(dto.getMotivo());
        entity.setCodigo(dto.getCodigo());
        entity.setIdProfessor(dto.getIdProfessor());
        entity.setIdSala(dto.getIdSala());
        entity.setIdAluno(dto.getIdAluno());

        return entity;
    }

    public static AulaExtraDTO toDto(AulaExtra entity) {
        if (entity == null) {
            return null;
        }

        AulaExtraDTO dto = new AulaExtraDTO();
        dto.setId(entity.getId());
        dto.setHorarioInicio(entity.getHorarioInicio());
        dto.setHorarioFim(entity.getHorarioFim());
        dto.setSituacao(entity.getSituacao());
        dto.setMotivo(entity.getMotivo());
        dto.setCodigo(entity.getCodigo());
        dto.setIdProfessor(entity.getIdProfessor());
        dto.setIdSala(entity.getIdSala());
        dto.setIdAluno(entity.getIdAluno());

        return dto;
    }
}