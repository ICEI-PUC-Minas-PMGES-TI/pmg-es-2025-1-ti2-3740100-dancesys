package com.dancesys.dancesys.mapper;

import com.dancesys.dancesys.dto.ApresentacaoEventoDTO;
import com.dancesys.dancesys.entity.ApresentacaoEvento;

public class ApresentacaoEventoMapper {
    public static ApresentacaoEvento toEntity(ApresentacaoEventoDTO dto){
        if(dto == null){
            return null;
        }

        ApresentacaoEvento entity = new ApresentacaoEvento();

        entity.setId(dto.getId());
        entity.setHoraInicio(dto.getHoraInicio());
        entity.setHoraFim(dto.getHoraFim());
        entity.setIdEvento(dto.getIdEvento());

        return entity;
    }

    public static ApresentacaoEventoDTO toDto(ApresentacaoEvento entity){
        if(entity == null){
            return null;
        }

        ApresentacaoEventoDTO dto = new ApresentacaoEventoDTO();

        dto.setId(entity.getId());
        dto.setHoraInicio(entity.getHoraInicio());
        dto.setHoraFim(entity.getHoraFim());
        dto.setIdEvento(entity.getIdEvento());

        return dto;
    }
}
