package com.dancesys.dancesys.mapper;

import com.dancesys.dancesys.dto.ProfessorModalidadeDTO;
import com.dancesys.dancesys.entity.Modalidade;
import com.dancesys.dancesys.entity.Professor;
import com.dancesys.dancesys.entity.ProfessorModalidade;

public class ProfessorModalidadeMapper {
    public static ProfessorModalidade toEntity(ProfessorModalidadeDTO dto) {
        if (dto == null) return null;

        ProfessorModalidade entity = new ProfessorModalidade();
        Professor prof = new Professor();
        Modalidade mod = new Modalidade();

        prof.setId(dto.getIdProfessor());
        mod.setId(dto.getIdModalidade());
        entity.setIdModalidade(mod);
        entity.setIdProfessor(prof);

        return entity;
    }

    public static ProfessorModalidadeDTO toDto(ProfessorModalidade entity) {
        if (entity == null) return null;

        ProfessorModalidadeDTO dto = new ProfessorModalidadeDTO();

        dto.setIdProfessor(entity.getIdProfessor().getId());
        dto.setIdModalidade(entity.getIdModalidade().getId());

        return dto;
    }
}
