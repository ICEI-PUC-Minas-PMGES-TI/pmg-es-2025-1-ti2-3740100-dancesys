package com.dancesys.dancesys.service;

import com.dancesys.dancesys.dto.ProfessorModalidadeDTO;
import com.dancesys.dancesys.entity.IdsCompostos.ProfessorModalidadeId;
import com.dancesys.dancesys.entity.ProfessorModalidade;
import com.dancesys.dancesys.mapper.ProfessorModalidadeMapper;
import com.dancesys.dancesys.repository.ProfessorModalidadeRepository;
import org.springframework.stereotype.Service;

@Service
public class ProfessorModalidadeServiceImpl implements ProfessorModalidadeService{

    private final ProfessorModalidadeRepository professorModalidadeRepository;

    public ProfessorModalidadeServiceImpl(ProfessorModalidadeRepository professorModalidadeRepository) {
        this.professorModalidadeRepository = professorModalidadeRepository;
    }

    public ProfessorModalidade salvar(ProfessorModalidadeDTO dto){
        ProfessorModalidadeId id = new ProfessorModalidadeId(dto.getIdProfessor(), dto.getIdModalidade());
        ProfessorModalidade professorModalidade = ProfessorModalidadeMapper.toEntity(dto);

        professorModalidade.setId(id);
        return professorModalidadeRepository.save(professorModalidade);
    }
}
