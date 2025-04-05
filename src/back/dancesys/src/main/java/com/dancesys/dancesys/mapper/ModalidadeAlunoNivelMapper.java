package com.dancesys.dancesys.mapper;

import com.dancesys.dancesys.dto.ModalidadeAlunoNivelDTO;
import com.dancesys.dancesys.entity.ModalidadeAlunoNivel;

public class ModalidadeAlunoNivelMapper {
    public static ModalidadeAlunoNivel toEntity(ModalidadeAlunoNivelDTO dto){
        if(dto == null) return null;

        ModalidadeAlunoNivel entity = new ModalidadeAlunoNivel();

        entity.setNivel(dto.getNivel());
        entity.setIdAluno(dto.getAluno());
        entity.setIdModalidade(dto.getModalidade());

        return entity;
    }

    public static ModalidadeAlunoNivelDTO toDto(ModalidadeAlunoNivel entity){
        if(entity == null) return null;

        ModalidadeAlunoNivelDTO dto = new ModalidadeAlunoNivelDTO();

        dto.setNivel(entity.getNivel());
        dto.setIdAluno(entity.getIdAluno().getId());
        dto.setIdModalidade(entity.getIdModalidade().getId());
        dto.setModalidade(entity.getIdModalidade());
        dto.setAluno(entity.getIdAluno());

        return  dto;
    }
}
