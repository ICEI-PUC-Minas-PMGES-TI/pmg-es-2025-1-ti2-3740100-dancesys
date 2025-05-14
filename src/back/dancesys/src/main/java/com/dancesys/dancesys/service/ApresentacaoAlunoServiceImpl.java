package com.dancesys.dancesys.service;

import com.dancesys.dancesys.entity.ApresentacaoAluno;
import com.dancesys.dancesys.entity.IdsCompostos.ApresentacaoAlunoId;
import com.dancesys.dancesys.mapper.ApresentacaoAlunoMapper;
import com.dancesys.dancesys.repository.ApresentacaoAlunoRepository;
import org.springframework.stereotype.Service;

@Service
public class ApresentacaoAlunoServiceImpl {
    private final ApresentacaoAlunoRepository apresentacaoAlunoRepository;

    public ApresentacaoAlunoServiceImpl(ApresentacaoAlunoRepository apresentacaoAlunoRepository) {
        this.apresentacaoAlunoRepository = apresentacaoAlunoRepository;
    }

    public void salvar(Long idApresentacao, Long idAluno) throws RuntimeException{
        try{
            ApresentacaoAlunoId id = new ApresentacaoAlunoId(idApresentacao, idAluno);
            ApresentacaoAluno entity = ApresentacaoAlunoMapper.toEntity(idApresentacao, idAluno);
            entity.setId(id);
            apresentacaoAlunoRepository.save(entity);
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }
}
