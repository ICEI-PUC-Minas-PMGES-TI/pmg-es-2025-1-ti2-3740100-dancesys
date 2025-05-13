package com.dancesys.dancesys.mapper;

import com.dancesys.dancesys.dto.FigurinoDTO;
import com.dancesys.dancesys.entity.Figurino;

public class FigurinoMapper {
    public static Figurino toEntity(FigurinoDTO dto){
        if(dto == null){
            return null;
        }

        Figurino entity = new Figurino();

        entity.setId(dto.getId());
        entity.setNome(dto.getNome());
        entity.setTipo(dto.getTipo());
        entity.setValor(dto.getValor());
        entity.setUrlFoto(dto.getUrlFoto());

        return entity;
    }

    public static FigurinoDTO toDto(Figurino entity){
        if(entity == null){
            return null;
        }

        FigurinoDTO dto = new FigurinoDTO();

        dto.setId(entity.getId());
        dto.setNome(entity.getNome());
        dto.setTipo(entity.getTipo());
        dto.setValor(entity.getValor());
        dto.setUrlFoto(entity.getUrlFoto());

        return dto;
    }
}