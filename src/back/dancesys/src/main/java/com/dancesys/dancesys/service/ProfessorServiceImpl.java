package com.dancesys.dancesys.service;

import com.dancesys.dancesys.entity.Professor;
import com.dancesys.dancesys.repository.ProfessorRepository;
import org.springframework.stereotype.Service;

@Service
public class ProfessorServiceImpl implements  ProfessorService {
    private final ProfessorRepository professorRepository;

    public ProfessorServiceImpl(ProfessorRepository professorRepository) {
        this.professorRepository = professorRepository;
    }

    public Professor salvar(Professor entity) throws Exception{
        try{
            Professor newEntity = professorRepository.save(entity);
            return newEntity;
        }catch(Exception e){
            throw new Exception(e.getMessage());
        }
    }
}
