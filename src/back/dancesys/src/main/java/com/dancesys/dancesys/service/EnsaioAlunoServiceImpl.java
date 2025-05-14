package com.dancesys.dancesys.service;

import com.dancesys.dancesys.entity.EnsaioAluno;
import com.dancesys.dancesys.entity.IdsCompostos.EnsaioAlunoId;
import com.dancesys.dancesys.mapper.EnsaioAlunoMapper;
import com.dancesys.dancesys.repository.EnsaioAlunoRepository;
import org.springframework.stereotype.Service;

@Service
public class EnsaioAlunoServiceImpl {
    private final EnsaioAlunoRepository ensaioAlunoRepository;

    public EnsaioAlunoServiceImpl(EnsaioAlunoRepository ensaioAlunoRepository) {
        this.ensaioAlunoRepository = ensaioAlunoRepository;
    }

    public void salvar(Long idEnsaio, Long idAluno) throws RuntimeException {
        try{
            EnsaioAluno entity = EnsaioAlunoMapper.toEntity(idEnsaio, idAluno);
            EnsaioAlunoId id = new EnsaioAlunoId(idEnsaio,idAluno);
            entity.setId(id);
            ensaioAlunoRepository.save(entity);
        }catch(Exception ex){
            throw new RuntimeException(ex);
        }

    }
}
