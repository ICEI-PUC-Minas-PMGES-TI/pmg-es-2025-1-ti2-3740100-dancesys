package com.dancesys.dancesys.repository;

import com.dancesys.dancesys.entity.AulaAluno;
import com.dancesys.dancesys.entity.IdsCompostos.AulaAlunoId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AulaAlunoRepository extends JpaRepository<AulaAluno, AulaAlunoId> {
}
