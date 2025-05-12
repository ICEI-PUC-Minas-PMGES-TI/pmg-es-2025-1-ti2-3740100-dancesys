package com.dancesys.dancesys.mapper;

import com.dancesys.dancesys.dto.IngressoEventoDTO;
import com.dancesys.dancesys.entity.IngressoEvento;

public class IngressoEventoMapper {
    public static IngressoEvento toEntity(IngressoEventoDTO dto) {
        if (dto == null) return null;

        IngressoEvento entity = new IngressoEvento();

        entity.setId(dto.getId());
        entity.setTipo(dto.getTipo());
        entity.setCodigo(dto.getCodigo());
        entity.setQuantidade(dto.getQuantidade());
        entity.setIdAluno(dto.getIdAluno());
        entity.setIdEvento(dto.getIdEvento());

        return entity;
    }

    public static IngressoEventoDTO toDto(IngressoEvento entity) {
        if (entity == null) return null;

        IngressoEventoDTO dto = new IngressoEventoDTO();

        dto.setId(entity.getId());
        dto.setTipo(entity.getTipo());
        dto.setCodigo(entity.getCodigo());
        dto.setQuantidade(entity.getQuantidade());
        dto.setIdAluno(entity.getIdAluno());
        dto.setIdEvento(entity.getIdEvento());

        return dto;
    }
}
