package com.dancesys.dancesys.repository;

import com.dancesys.dancesys.dto.ProfessorModalidadeDTO;
import com.dancesys.dancesys.entity.IdsCompostos.ProfessorModalidadeId;
import com.dancesys.dancesys.entity.ProfessorModalidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProfessorModalidadeRepository extends JpaRepository<ProfessorModalidade, ProfessorModalidadeId> {
    List<ProfessorModalidade> findByIdProfessorIdAndIdModalidadeIdNotIn(Long idProfessor, List<Long> mods);

    List<ProfessorModalidade> findByIdProfessorId(Long idProfessor);
}
