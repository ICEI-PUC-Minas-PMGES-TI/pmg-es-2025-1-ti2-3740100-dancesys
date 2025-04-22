package com.dancesys.dancesys.mapper;

import com.dancesys.dancesys.entity.Aluno;
import com.dancesys.dancesys.entity.AulaOcorrencia;
import com.dancesys.dancesys.entity.Chamada;
import com.dancesys.dancesys.entity.IdsCompostos.ChamadaId;

public class ChamadaMapper {
    public static Chamada toEntity(Long aulaId, Long alunoId, Integer status){
        Chamada chamada = new Chamada();

        ChamadaId id = new ChamadaId(alunoId,aulaId);
        chamada.setId(id);

        AulaOcorrencia ao = new AulaOcorrencia();
        ao.setId(aulaId);
        chamada.setIdAulaOcorrencia(ao);

        Aluno aluno = new Aluno();
        aluno.setId(alunoId);
        chamada.setIdAluno(aluno);

        chamada.setPresenca(status);

        return chamada;
    }
}
