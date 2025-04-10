package com.dancesys.dancesys.mapper;

import com.dancesys.dancesys.dto.DividendoDTO;
import com.dancesys.dancesys.entity.Dividendo;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class DividendoMapper {
    public static Dividendo toEntity(DividendoDTO dto){
        if(dto == null) return null;

        Dividendo entity = new Dividendo();

        entity.setId(dto.getId());
        entity.setValor(dto.getValor());
        entity.setCriadoEm(dto.getCriadoEm());
        entity.setPagoEm(dto.getPagoEm());
        entity.setTipo(dto.getTipo());
        entity.setStatus(dto.getStatus());
        entity.setCodigo(dto.getCodigo());
        entity.setIdAluno(dto.getIdAluno());

        return entity;
    }

    public static DividendoDTO toDto(Dividendo entity){
        if(entity == null) return null;

        DividendoDTO dto = new DividendoDTO();

        dto.setId(entity.getId());
        dto.setValor(entity.getValor());
        dto.setCriadoEm(entity.getCriadoEm());
        dto.setPagoEm(entity.getPagoEm());
        if(entity.getStatus().equals(Dividendo.atrasado)){
            dto.setMesesAtrasos(ChronoUnit.MONTHS.between(entity.getCriadoEm(), LocalDate.now()));
        }
        dto.setTipo(entity.getTipo());
        dto.setCodigo(entity.getCodigo());
        dto.setStatus(entity.getStatus());
        dto.setIdAluno(entity.getIdAluno());

        return dto;
    }
}
