package com.dancesys.dancesys.mapper;

import com.dancesys.dancesys.dto.SalaDTO;
import com.dancesys.dancesys.entity.Sala;

public class SalaMapper {
    public static Sala toEntity(SalaDTO dto){
        if(dto == null) return null;

        Sala entity = new Sala();

        entity.setId(dto.getId());
        entity.setNome(dto.getNome());

        return entity;
    }

    public static SalaDTO toDto(Sala entity){
        if(entity == null) return null;

        SalaDTO dto = new SalaDTO();

        dto.setId(entity.getId());
        dto.setNome(entity.getNome());

        return dto;
    }
}
