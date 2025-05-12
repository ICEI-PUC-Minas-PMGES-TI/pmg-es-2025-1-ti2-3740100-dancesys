package com.dancesys.dancesys.mapper;

import com.dancesys.dancesys.dto.EnsaioApresentacaoDTO;
import com.dancesys.dancesys.entity.EnsaioApresentacao;

public class EnsaioApresentacaoMapper {
    public static EnsaioApresentacao toEntity(EnsaioApresentacaoDTO dto){
        if(dto == null){
            return null;
        }

        EnsaioApresentacao entity = new EnsaioApresentacao();

        entity.setId(dto.getId());
        entity.setDataHoraInicio(dto.getDataHoraInicio());
        entity.setDataHoraFim(dto.getDataHoraFim());
        entity.setIdProfessor(dto.getIdProfessor());
        entity.setIdApresentacaoEvento(dto.getIdApresentacaoEvento());

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
        dto.setIdProfessor(entity.getIdProfessor());
        dto.setIdApresentacaoEvento(entity.getIdApresentacaoEvento());

        return dto;
    }
}