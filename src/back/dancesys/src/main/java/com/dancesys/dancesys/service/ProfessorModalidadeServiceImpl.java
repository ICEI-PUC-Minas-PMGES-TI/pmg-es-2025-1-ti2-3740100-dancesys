package com.dancesys.dancesys.service;

import com.dancesys.dancesys.dto.ProfessorModalidadeDTO;
import com.dancesys.dancesys.entity.IdsCompostos.ProfessorModalidadeId;
import com.dancesys.dancesys.entity.ProfessorModalidade;
import com.dancesys.dancesys.mapper.ProfessorModalidadeMapper;
import com.dancesys.dancesys.repository.ProfessorModalidadeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfessorModalidadeServiceImpl{

    private final ProfessorModalidadeRepository professorModalidadeRepository;

    public ProfessorModalidadeServiceImpl(ProfessorModalidadeRepository professorModalidadeRepository) {
        this.professorModalidadeRepository = professorModalidadeRepository;
    }

    public ProfessorModalidade salvar(ProfessorModalidade entity){
        ProfessorModalidadeId id = new ProfessorModalidadeId(entity.getIdProfessor().getId(), entity.getIdModalidade().getId());

        entity.setId(id);
        return professorModalidadeRepository.save(entity);
    }

    public void excluirAll(Long idProfessor, List<Long> idMods){
        List<ProfessorModalidade> mods = professorModalidadeRepository.findByIdProfessorIdAndIdModalidadeIdNotIn(idProfessor, idMods);
        professorModalidadeRepository.deleteAll(mods);
    }

    public void excluirAllPorIdProfessor(Long idProfessor){
        List<ProfessorModalidade> mods = professorModalidadeRepository.findByIdProfessorId(idProfessor);
        professorModalidadeRepository.deleteAll(mods);
    }
}
