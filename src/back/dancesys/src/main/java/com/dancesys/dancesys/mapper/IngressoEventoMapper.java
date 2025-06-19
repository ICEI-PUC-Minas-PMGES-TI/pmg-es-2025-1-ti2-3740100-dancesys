package com.dancesys.dancesys.mapper;

import com.dancesys.dancesys.dto.IngressoEventoDTO;
import com.dancesys.dancesys.entity.Aluno;
import com.dancesys.dancesys.entity.Evento;
import com.dancesys.dancesys.entity.IngressoEvento;

public class IngressoEventoMapper {
    public static IngressoEvento toEntity(IngressoEventoDTO dto) {
        if (dto == null) return null;

        IngressoEvento entity = new IngressoEvento();

        entity.setId(dto.getId());
        entity.setTipo(dto.getTipo());
        entity.setCodigo(dto.getCodigo());
        entity.setQuantidade(dto.getQuantidade());

        Aluno aluno = new Aluno();
        aluno.setId(dto.getIdAluno());
        entity.setIdAluno(aluno);

        Evento evento = new Evento();
        evento.setId(dto.getIdEvento());
        entity.setIdEvento(evento);

        return entity;
    }

    public static IngressoEventoDTO toDto(IngressoEvento entity) {
        if (entity == null) return null;

        IngressoEventoDTO dto = new IngressoEventoDTO();

        dto.setId(entity.getId());
        dto.setTipo(entity.getTipo());
        dto.setCodigo(entity.getCodigo());
        dto.setQuantidade(entity.getQuantidade());
        dto.setIdAluno(entity.getIdAluno().getId());
        dto.setIdEvento(entity.getIdEvento().getId());

        return dto;
    }
}
