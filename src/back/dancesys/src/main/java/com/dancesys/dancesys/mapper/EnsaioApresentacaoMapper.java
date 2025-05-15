package com.dancesys.dancesys.mapper;

import com.dancesys.dancesys.dto.EnsaioApresentacaoDTO;
import com.dancesys.dancesys.entity.ApresentacaoEvento;
import com.dancesys.dancesys.entity.EnsaioApresentacao;
import com.dancesys.dancesys.entity.Professor;

public class EnsaioApresentacaoMapper {
    public static EnsaioApresentacao toEntity(EnsaioApresentacaoDTO dto){
        if(dto == null){
            return null;
        }

        EnsaioApresentacao entity = new EnsaioApresentacao();

        entity.setId(dto.getId());
        entity.setDataHoraInicio(dto.getDataHoraInicio());
        entity.setDataHoraFim(dto.getDataHoraFim());

        Professor professor = new Professor();
        professor.setId(dto.getIdProfessor());
        entity.setIdProfessor(professor);

        ApresentacaoEvento apresentacaoEvento = new ApresentacaoEvento();
        apresentacaoEvento.setId(dto.getIdApresentacaoEvento());
        entity.setIdApresentacaoEvento(apresentacaoEvento);

        return entity;
    }

    public static EnsaioApresentacaoDTO toDto(EnsaioApresentacao entity){
        if(entity == null){
            return null;
        }

        EnsaioApresentacaoDTO dto = new EnsaioApresentacaoDTO();

        dto.setId(entity.getId());
        dto.setDataHoraInicio(entity.getDataHoraInicio());
        dto.setDataHoraFim(entity.getDataHoraFim());
        dto.setIdProfessor(entity.getIdProfessor().getId());
        dto.setIdApresentacaoEvento(entity.getIdApresentacaoEvento().getId());

        return dto;
    }
}