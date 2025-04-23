package com.dancesys.dancesys.mapper;

import com.dancesys.dancesys.dto.AulaDTO;
import com.dancesys.dancesys.entity.Aula;
import com.dancesys.dancesys.entity.Modalidade;
import com.dancesys.dancesys.entity.Professor;
import com.dancesys.dancesys.entity.Sala;

public class AulaMapper {
    public static AulaDTO toDto(Aula entity){
        if(entity == null) return null;

        AulaDTO dto = new AulaDTO();

        dto.setId(entity.getId());
        dto.setDiaSemana(entity.getDiaSemana());
        dto.setHorarioInicio(entity.getHorarioInicio());
        dto.setHorarioFim(entity.getHorarioFim());
        dto.setMaxAlunos(entity.getMaxAlunos());
        dto.setNivel(entity.getNivel());
        dto.setStatus(entity.getStatus());
        dto.setIdSala(entity.getIdSala().getId());
        dto.setIdModalidade(entity.getIdModalidade().getId());
        dto.setIdProfessor(entity.getIdProfessor().getId());

        return  dto;
    }

    public static Aula toEntity(AulaDTO dto){
        if(dto == null) return null;

        Aula entity = new Aula();

        entity.setId(dto.getId());
        entity.setDiaSemana(dto.getDiaSemana());
        entity.setHorarioInicio(dto.getHorarioInicio());
        entity.setHorarioFim(dto.getHorarioFim());
        entity.setMaxAlunos(dto.getMaxAlunos());
        entity.setNivel(dto.getNivel());
        entity.setStatus(dto.getStatus());

        Sala sala = new Sala();
        sala.setId(dto.getIdSala());
        entity.setIdSala(sala);

        Modalidade mod = new Modalidade();
        mod.setId(dto.getIdModalidade());
        entity.setIdModalidade(mod);

        Professor prof = new Professor();
        prof.setId(dto.getIdProfessor());
        entity.setIdProfessor(prof);

        return entity;
    }
}
