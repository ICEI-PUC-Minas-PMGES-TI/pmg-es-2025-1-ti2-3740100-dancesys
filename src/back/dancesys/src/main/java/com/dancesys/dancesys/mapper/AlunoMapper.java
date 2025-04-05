package com.dancesys.dancesys.mapper;

import com.dancesys.dancesys.dto.AlunoDTO;
import com.dancesys.dancesys.entity.Aluno;

public class AlunoMapper {
    public static Aluno toEntity(AlunoDTO dto){
        if(dto == null) return null;

        Aluno entity = new Aluno();

        entity.setId(dto.getId());
        entity.setCreditos(dto.getCreditos());
        entity.setTipo(dto.getTipo());
        if(dto.getBoolBaile().equals(true)){
            entity.setBoolBaile(Aluno.sim);
        }else if(dto.getBoolBaile().equals(false)){
            entity.setBoolBaile(Aluno.nao);
        }
        entity.setIdUsuario(dto.getIdUsuario());

        return entity;
    }
}
