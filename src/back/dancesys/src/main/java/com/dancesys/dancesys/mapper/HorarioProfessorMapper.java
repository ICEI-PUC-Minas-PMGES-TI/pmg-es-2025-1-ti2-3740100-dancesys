package com.dancesys.dancesys.mapper;

import com.dancesys.dancesys.dto.HorarioProfessorDTO;
import com.dancesys.dancesys.entity.HorarioProfessor;
import com.dancesys.dancesys.entity.Professor;

public class HorarioProfessorMapper {
    public static HorarioProfessor toEntity(HorarioProfessorDTO dto){
        if (dto == null) return null;

        HorarioProfessor entity = new HorarioProfessor();

        entity.setId(dto.getId());
        entity.setDiaSemana(dto.getDiaSemana());
        entity.setHorarioEntrada(dto.getHorarioEntrada());
        entity.setHorarioSaida(dto.getHorarioSaida());
        Professor prof = new Professor();
        prof.setId(dto.getIdProfessor());
        entity.setIdProfessor(prof);

        return entity;
    }

    public static HorarioProfessorDTO toDto( HorarioProfessor entity){
        if (entity == null) return null;

        HorarioProfessorDTO dto = new HorarioProfessorDTO();

        dto.setId(entity.getId());
        dto.setDiaSemana(entity.getDiaSemana());
        dto.setHorarioEntrada(entity.getHorarioEntrada());
        dto.setHorarioSaida(entity.getHorarioSaida());
        dto.setIdProfessor(entity.getIdProfessor().getId());

        return dto;
    }
}
