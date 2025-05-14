package com.dancesys.dancesys.mapper;

import com.dancesys.dancesys.entity.Aluno;
import com.dancesys.dancesys.entity.EnsaioAluno;
import com.dancesys.dancesys.entity.EnsaioApresentacao;

public class EnsaioAlunoMapper {
    public static EnsaioAluno toEntity(Long idEnsaio, Long idAluno){
        EnsaioAluno entity = new EnsaioAluno();

        EnsaioApresentacao ensaio = new EnsaioApresentacao();
        ensaio.setId(idEnsaio);
        entity.setIdEnsaio(ensaio);

        Aluno aluno = new Aluno();
        aluno.setId(idAluno);
        entity.setIdAluno(aluno);

        return entity;
    }
}
