package com.dancesys.dancesys.mapper;

import com.dancesys.dancesys.dto.ModalidadeAlunoNivelDTO;
import com.dancesys.dancesys.entity.Aluno;
import com.dancesys.dancesys.entity.Modalidade;
import com.dancesys.dancesys.entity.ModalidadeAlunoNivel;

public class ModalidadeAlunoNivelMapper {
    public static ModalidadeAlunoNivel toEntity(ModalidadeAlunoNivelDTO dto){
        if(dto == null) return null;

        ModalidadeAlunoNivel entity = new ModalidadeAlunoNivel();
        Modalidade mod =  new Modalidade();
        Aluno aluno = new Aluno();

        entity.setNivel(dto.getNivel());
        mod.setId(dto.getIdModalidade());
        aluno.setId(dto.getIdAluno());
        entity.setIdAluno(aluno);
        entity.setIdModalidade(mod);

        return entity;
    }

    public static ModalidadeAlunoNivelDTO toDto(ModalidadeAlunoNivel entity){
        if(entity == null) return null;

        ModalidadeAlunoNivelDTO dto = new ModalidadeAlunoNivelDTO();

        dto.setNivel(entity.getNivel());
        dto.setIdAluno(entity.getIdAluno().getId());
        dto.setIdModalidade(entity.getIdModalidade().getId());

        return  dto;
    }
}
