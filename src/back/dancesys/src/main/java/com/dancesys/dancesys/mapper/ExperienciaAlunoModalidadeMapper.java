package com.dancesys.dancesys.mapper;

import com.dancesys.dancesys.dto.ExperienciaAlunoModalidadeDto;
import com.dancesys.dancesys.entity.ExperienciaAlunoModalidade;

public class ExperienciaAlunoModalidadeMapper {
    public static ExperienciaAlunoModalidade toEntity(ExperienciaAlunoModalidadeDto dto){
        if(dto == null) return null;

        ExperienciaAlunoModalidade entity = new ExperienciaAlunoModalidade();

        entity.setId(dto.getId());
        entity.setNivel(dto.getNivel());
        entity.setIdAluno(dto.getIdAluno());
        return entity;
    }

    public static ExperienciaAlunoModalidadeDto toDto(ExperienciaAlunoModalidade entity){
        if(entity == null) return null;

        ExperienciaAlunoModalidadeDto dto = new ExperienciaAlunoModalidadeDto();

        dto.setId(entity.getId());
        dto.setNivel(entity.getNivel());
        dto.setIdAluno(entity.getIdAluno());

        return dto;
    }
}
