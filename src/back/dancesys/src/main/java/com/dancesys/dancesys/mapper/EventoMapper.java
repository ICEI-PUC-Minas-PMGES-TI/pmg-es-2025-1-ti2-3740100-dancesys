package com.dancesys.dancesys.mapper;

import com.dancesys.dancesys.dto.EventoDTO;
import com.dancesys.dancesys.entity.Evento;

public class EventoMapper {
    public static Evento toEntity(EventoDTO dto){
        if(dto == null) return null;

        Evento entity = new Evento();

        entity.setId(dto.getId());
        entity.setNome(dto.getNome());
        entity.setLocal(dto.getLocal());
        entity.setDataHoraInicio(dto.getDataHoraInicio());
        entity.setDataHoraFim(dto.getDataHoraFim());
        entity.setValor(dto.getValor());
        entity.setUrlFoto(dto.getUrlFoto());

        return entity;
    }


    public static EventoDTO toDto(Evento entity){
        if(entity == null) return null;

        EventoDTO dto = new EventoDTO();

        dto.setId(entity.getId());
        dto.setNome(entity.getNome());
        dto.setLocal(entity.getLocal());
        dto.setDataHoraInicio(entity.getDataHoraInicio());
        dto.setDataHoraFim(entity.getDataHoraFim());
        dto.setValor(entity.getValor());
        dto.setUrlFoto(entity.getUrlFoto());

        return dto;
    }
}
