package com.dancesys.dancesys.mapper;

import com.dancesys.dancesys.dto.ProfessorModalidadeDTO;
import com.dancesys.dancesys.entity.ProfessorModalidade;

public class ProfessorModalidadeMapper {
    public static ProfessorModalidade toEntity(ProfessorModalidadeDTO dto) {
        if (dto == null) return null;

        ProfessorModalidade entity = new ProfessorModalidade();

        entity.setId(dto.getId());
        entity.setIdModalidade(dto.getModalidade());
        entity.setIdProfessor(dto.getProfessor());

        return entity;
    }

    public static ProfessorModalidadeDTO toDto(ProfessorModalidade entity) {
        if (entity == null) return null;

        ProfessorModalidadeDTO dto = new ProfessorModalidadeDTO();

        dto.setId(entity.getId());
        dto.setProfessor(entity.getIdProfessor());
        dto.setModalidade(entity.getIdModalidade());
        dto.setIdProfessor(entity.getIdProfessor().getId());
        dto.setIdModalidade(entity.getIdModalidade().getId());

        return dto;
    }
}
