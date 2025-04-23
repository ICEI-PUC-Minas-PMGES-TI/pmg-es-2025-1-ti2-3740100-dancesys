package com.dancesys.dancesys.service;

import com.dancesys.dancesys.entity.Aluno;
import com.dancesys.dancesys.repository.AlunoRepository;
import org.springframework.stereotype.Service;

@Service
public class AlunoServiceImpl{
    private final AlunoRepository alunoRepository;

    public AlunoServiceImpl(AlunoRepository alunoRepository) {
        this.alunoRepository = alunoRepository;
    }

    public Aluno salvar(Aluno entity) throws Exception{

        try{
            if(entity.getTipo().equals(Aluno.fixo)){
                entity.setCreditos(0);
            }else{
                entity.setCreditos(Aluno.max_creditos);
            }
            Aluno newEntity = alunoRepository.save(entity);
            return newEntity;
        }catch(Exception e){
            throw new Exception(e.getMessage());
        }
    }

    public Aluno dimuirCredito(Aluno entity, Integer n){
        if(entity.getCreditos().equals(0)){
            throw new RuntimeException("Aluno ja com o minimo de craditos");
        }
        entity.setCreditos(entity.getCreditos() - n);
        return alunoRepository.save(entity);
    }

    public Aluno aumentarCredito(Aluno entity, Integer n){
        if(entity.getTipo().equals(Aluno.fixo)){
            throw new RuntimeException("Aluno do tipo fixo");
        }
        if(entity.getCreditos().equals(Aluno.max_creditos)){
            throw new RuntimeException("Aluno ja com creditos maximos");
        }

        entity.setCreditos(entity.getCreditos() + n);
        return alunoRepository.save(entity);
    }

    public Aluno findById(Long id){
        return alunoRepository.findById(id);
    }
}
