package com.dancesys.dancesys.mapper;

import com.dancesys.dancesys.dto.AulaExtraDTO;
import com.dancesys.dancesys.entity.Aluno;
import com.dancesys.dancesys.entity.AulaExtra;
import com.dancesys.dancesys.entity.Professor;
import com.dancesys.dancesys.entity.Sala;

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

        Professor professor = new Professor();
        professor.setId(dto.getIdProfessor());
        entity.setIdProfessor(professor);


        if (dto.getIdSala() != null) {
            Sala sala = new Sala();
            sala.setId(dto.getIdSala());
            entity.setIdSala(sala);
        } else {
            entity.setIdSala(null);
        }

        Aluno aluno = new Aluno();
        aluno.setId(dto.getIdAluno());
        entity.setIdAluno(aluno);

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
        dto.setIdProfessor(entity.getIdProfessor().getId());
        if(entity.getIdSala() != null) {
            dto.setIdSala(entity.getIdSala().getId());
        }else{
            dto.setIdSala(null);
        }
        dto.setIdAluno(entity.getIdAluno().getId());

        return dto;
    }
}