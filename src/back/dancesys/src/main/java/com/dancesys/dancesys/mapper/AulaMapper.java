package com.dancesys.dancesys.mapper;

import com.dancesys.dancesys.dto.AulaDto;
import com.dancesys.dancesys.entity.Aula;

public class AulaMapper {
    public static AulaDto toDto(Aula a) {
        AulaDto dto = new AulaDto();

        dto.setId(a.getId());
        dto.setDiaSemana(a.getDiaSemana());
        dto.setTipo(a.getTipo());
        dto.setHorarioInicio(a.getHorarioInicio());
        dto.setHorarioFim(a.getHorarioFim());
        dto.setSala(a.getSala());
        dto.setIdProfessor(a.getIdProfessor());
        dto.setListaIdsAlunos(a.getListaIdsAlunos());
        dto.setMaxAlunos(a.getMaxAlunos());
        dto.setStatus(a.getStatus());

        return dto;
    }

    public static Aula toEntity(AulaDto a) {
        Aula entity = new Aula();

        entity.setId(a.getId());
        entity.setDiaSemana(a.getDiaSemana());
        entity.setTipo(a.getTipo());
        entity.setHorarioInicio(a.getHorarioInicio());
        entity.setHorarioFim(a.getHorarioFim());
        entity.setSala(a.getSala());
        entity.setIdProfessor(a.getIdProfessor());
        entity.setListaIdsAlunos(a.getListaIdsAlunos());
        entity.setMaxAlunos(a.getMaxAlunos());
        entity.setStatus(a.getStatus());

        return entity;
    }
}
