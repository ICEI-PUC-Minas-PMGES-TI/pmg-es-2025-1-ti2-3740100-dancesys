package com.dancesys.dancesys.repository;

import com.dancesys.dancesys.entity.IdsCompostos.ProfessorModalidadeId;
import com.dancesys.dancesys.entity.ProfessorModalidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfessorModalidadeRepository extends JpaRepository<ProfessorModalidade, ProfessorModalidadeId> {
}
