package com.dancesys.dancesys.service;

import com.dancesys.dancesys.dto.ProfessorFilter;
import com.dancesys.dancesys.entity.Professor;
import com.dancesys.dancesys.infra.PaginatedResponse;
import com.dancesys.dancesys.repository.ProfessorCustomRepository;
import com.dancesys.dancesys.repository.ProfessorRepository;
import com.dancesys.dancesys.repository.ProfessorRepositoryCustom;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfessorServiceImpl {
    private final ProfessorRepository professorRepository;
    private final ProfessorCustomRepository professorCustomRepository;
    private final ProfessorRepositoryCustom  professorRepositoryCustom;

    public ProfessorServiceImpl(
            ProfessorRepository professorRepository,
            ProfessorCustomRepository professorCustomRepository,
            ProfessorRepositoryCustom professorRepositoryCustom
    ) {
        this.professorRepository = professorRepository;
        this.professorCustomRepository = professorCustomRepository;
        this.professorRepositoryCustom = professorRepositoryCustom;
    }

    public Professor salvar(Professor entity) throws Exception{
        try{
            Professor newEntity = professorRepository.save(entity);
            return newEntity;
        }catch(Exception e){
            throw new Exception(e.getMessage());
        }
    }

    public PaginatedResponse<Professor> buscarProfessores(ProfessorFilter filtro){
        return professorCustomRepository.buscar(filtro);
    }

    public PaginatedResponse<Professor> buscar(ProfessorFilter filtro){
        return professorCustomRepository.buscar(filtro);
    }

    public Professor findByIdUsuario(Long id){
        return professorRepository.findByIdUsuarioId(id);
    }
}
