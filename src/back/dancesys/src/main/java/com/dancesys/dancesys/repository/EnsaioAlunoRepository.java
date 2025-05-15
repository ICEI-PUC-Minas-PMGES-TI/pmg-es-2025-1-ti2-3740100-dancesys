package com.dancesys.dancesys.repository;

import com.dancesys.dancesys.entity.EnsaioAluno;
import com.dancesys.dancesys.entity.IdsCompostos.EnsaioAlunoId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnsaioAlunoRepository extends JpaRepository<EnsaioAluno, EnsaioAlunoId> {
}
