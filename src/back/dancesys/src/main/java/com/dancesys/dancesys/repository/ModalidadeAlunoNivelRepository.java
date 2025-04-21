package com.dancesys.dancesys.repository;

import com.dancesys.dancesys.entity.Aluno;
import com.dancesys.dancesys.entity.IdsCompostos.AlunoModalidade;
import com.dancesys.dancesys.entity.ModalidadeAlunoNivel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ModalidadeAlunoNivelRepository extends JpaRepository<ModalidadeAlunoNivel, AlunoModalidade> {
    public List<ModalidadeAlunoNivel> findByIdAluno_Id(Long id);

}
