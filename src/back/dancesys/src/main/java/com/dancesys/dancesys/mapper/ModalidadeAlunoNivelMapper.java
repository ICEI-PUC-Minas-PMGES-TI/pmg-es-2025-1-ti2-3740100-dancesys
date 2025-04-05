package com.dancesys.dancesys.mapper;

import com.dancesys.dancesys.dto.ModalidadeAlunoNivelDTO;
import com.dancesys.dancesys.entity.ModalidadeAlunoNivel;

public class ModalidadeAlunoNivelMapper {
    public static ModalidadeAlunoNivel toEntity(ModalidadeAlunoNivelDTO dto){
        if(dto == null) return null;

        ModalidadeAlunoNivel entity = new ModalidadeAlunoNivel();

        entity.setId(dto.getId());
        entity.setNivel(dto.getNivel());
        entity.setIdAluno(dto.getIdAluno());
        entity.setIdModalidade(dto.getIdModalidade());

        return entity;
    }

    public static ModalidadeAlunoNivelDTO toDto(ModalidadeAlunoNivel entity){
        if(entity == null) return null;

        ModalidadeAlunoNivelDTO dto = new ModalidadeAlunoNivelDTO();

        dto.setId(entity.getId());
        dto.setNivel(entity.getNivel());
        dto.setIdAluno(entity.getIdAluno());
        dto.setIdModalidade(entity.getIdModalidade());

        return  dto;
    }
}
