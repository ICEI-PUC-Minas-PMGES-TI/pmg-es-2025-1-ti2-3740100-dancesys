package com.dancesys.dancesys.mapper;

import com.dancesys.dancesys.entity.Aluno;
import com.dancesys.dancesys.entity.ApresentacaoAluno;
import com.dancesys.dancesys.entity.ApresentacaoEvento;

public class ApresentacaoAlunoMapper {
    public static ApresentacaoAluno toEntity(Long idApresentacao, Long idAluno) {
        ApresentacaoAluno entity = new ApresentacaoAluno();

        ApresentacaoEvento apresentacao = new ApresentacaoEvento();
        apresentacao.setId(idApresentacao);
        entity.setIdApresentacao(apresentacao);

        Aluno aluno = new Aluno();
        aluno.setId(idAluno);
        entity.setIdAluno(aluno);

        return entity;
    }
}
