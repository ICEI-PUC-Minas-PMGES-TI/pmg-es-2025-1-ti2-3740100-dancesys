package com.dancesys.dancesys.mapper;

import com.dancesys.dancesys.dto.ModalidadeDTO;
import com.dancesys.dancesys.entity.Modalidade;

public class ModalideMapper {
    public static ModalidadeDTO toDto(Modalidade entity){
        if(entity == null) return null;

        ModalidadeDTO dto = new ModalidadeDTO();
        dto.setId(entity.getId());
        dto.setNome(entity.getNome());

        return  dto;
    }

    public static Modalidade toEntity(ModalidadeDTO dto){
        if(dto == null) return null;

        Modalidade entity = new Modalidade();
        entity.setId(dto.getId());
        entity.setNome(dto.getNome());

        return  entity;
    }
}
