package com.dancesys.dancesys.service;

import com.dancesys.dancesys.entity.AulaAluno;
import com.dancesys.dancesys.entity.IdsCompostos.AulaAlunoId;
import com.dancesys.dancesys.mapper.AulaAlunoMapper;
import com.dancesys.dancesys.repository.AulaAlunoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AulaAlunoServiceImpl {
    private final AulaAlunoRepository aulaAlunoRepository;

    public AulaAlunoServiceImpl(AulaAlunoRepository aulaAlunoRepository) {
        this.aulaAlunoRepository = aulaAlunoRepository;
    }

    public void salvar(List<Long> ids, Long idAula) {
        for (Long idAluno : ids) {
            AulaAlunoId id = new AulaAlunoId(idAula, idAluno);
            aulaAlunoRepository.save(AulaAlunoMapper.toEntity(id,idAluno,idAula));
        }
    }

    public List<AulaAluno> buscarPorAula(Long idAula) {
        return aulaAlunoRepository.findByIdAula_Id(idAula);
    }
}
