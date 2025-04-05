package com.dancesys.dancesys.service;

import com.dancesys.dancesys.entity.Aluno;
import com.dancesys.dancesys.repository.AlunoRepository;
import org.springframework.stereotype.Service;

@Service
public class AlunoServiceImpl implements AlunoService{
    private final AlunoRepository alunoRepository;

    public AlunoServiceImpl(AlunoRepository alunoRepository) {
        this.alunoRepository = alunoRepository;
    }

    public Aluno salvar(Aluno entity) throws Exception{
        try{
            Aluno salvo = alunoRepository.save(entity);
            return salvo;
        }catch(Exception e){
            throw new Exception(e.getMessage());
        }
    }
}
