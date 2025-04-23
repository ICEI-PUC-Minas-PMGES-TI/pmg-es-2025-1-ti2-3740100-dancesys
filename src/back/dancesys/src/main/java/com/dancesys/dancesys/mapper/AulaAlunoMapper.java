package com.dancesys.dancesys.mapper;

import com.dancesys.dancesys.entity.Aluno;
import com.dancesys.dancesys.entity.Aula;
import com.dancesys.dancesys.entity.AulaAluno;
import com.dancesys.dancesys.entity.IdsCompostos.AulaAlunoId;

public class AulaAlunoMapper {
    public static AulaAluno toEntity(AulaAlunoId idAulaAluno, Long idAluno, Long idAula) {
        AulaAluno entity = new AulaAluno();

        entity.setId(idAulaAluno);

        Aula aula = new Aula();
        aula.setId(idAula);
        entity.setIdAula(aula);

        Aluno aluno = new Aluno();
        aluno.setId(idAluno);
        entity.setIdAluno(aluno);

        return entity;
    }
}
