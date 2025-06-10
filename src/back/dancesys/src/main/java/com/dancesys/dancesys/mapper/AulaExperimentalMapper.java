package com.dancesys.dancesys.mapper;

import com.dancesys.dancesys.dto.AulaExperimentalDTO;
import com.dancesys.dancesys.entity.AulaExperimental;
import com.dancesys.dancesys.entity.Professor;

public class AulaExperimentalMapper {
    public static AulaExperimental toEntity(AulaExperimentalDTO dto){
        if(dto == null) return null;

        AulaExperimental entity = new AulaExperimental();

        entity.setId(dto.getId());
        entity.setDataHorarioInicio(dto.getData().atTime(dto.getHorarioInicio()));
        entity.setDataHorarioFim(dto.getData().atTime(dto.getHorarioFim()));
        entity.setNome(dto.getNome());
        entity.setCpf(dto.getCpf());
        entity.setNumero(dto.getNumero());
        entity.setSituacao(dto.getSituacao());
        entity.setMotivo(dto.getMotivo());
        entity.setMotivoOutro(dto.getMotivoOutro());
        entity.setCriadoEm(dto.getCriadoEm());
        entity.setFinalizadoEm(dto.getFinalizadoEm());

        Professor professor = new Professor();
        professor.setId(dto.getIdProfessor());
        entity.setIdProfessor(professor);

        return entity;
    }

    public static AulaExperimentalDTO toDto(AulaExperimental entity){
        if(entity == null) return null;

        AulaExperimentalDTO dto = new AulaExperimentalDTO();

        dto.setId(entity.getId());
        dto.setData(entity.getDataHorarioInicio().toLocalDate());
        dto.setHorarioInicio(entity.getDataHorarioInicio().toLocalTime());
        dto.setHorarioFim(entity.getDataHorarioFim().toLocalTime());
        dto.setNome(entity.getNome());
        dto.setCpf(entity.getCpf());
        dto.setNumero(entity.getNumero());
        dto.setSituacao(entity.getSituacao());
        dto.setMotivo(entity.getMotivo());
        dto.setMotivoOutro(entity.getMotivoOutro());
        dto.setCriadoEm(entity.getCriadoEm());
        dto.setFinalizadoEm(entity.getFinalizadoEm());
        dto.setIdProfessor(entity.getIdProfessor().getId());

        return dto;
    }
}
