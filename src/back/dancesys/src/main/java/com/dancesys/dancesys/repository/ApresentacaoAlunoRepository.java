package com.dancesys.dancesys.repository;

import com.dancesys.dancesys.entity.ApresentacaoAluno;
import com.dancesys.dancesys.entity.IdsCompostos.ApresentacaoAlunoId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApresentacaoAlunoRepository extends JpaRepository<ApresentacaoAluno, ApresentacaoAlunoId> {
}
