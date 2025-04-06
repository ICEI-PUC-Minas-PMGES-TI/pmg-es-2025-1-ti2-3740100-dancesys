package com.dancesys.dancesys.mapper;

import com.dancesys.dancesys.dto.ProfessorDTO;
import com.dancesys.dancesys.entity.Professor;

public class ProfessorMapper {
    public static Professor toEntity(ProfessorDTO dto){
        if(dto == null) return null;

        Professor entity = new Professor();

        entity.setId(dto.getId());
        entity.setInformacoesProfissionais(dto.getInformacoesProfissionais());

        return entity;
    }
}
