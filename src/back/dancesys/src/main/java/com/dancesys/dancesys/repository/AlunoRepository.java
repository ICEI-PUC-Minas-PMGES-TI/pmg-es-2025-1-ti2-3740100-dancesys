package com.dancesys.dancesys.repository;

import com.dancesys.dancesys.entity.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlunoRepository extends JpaRepository<Aluno, Integer> {

        Aluno findById(Long id);

        Aluno findByIdUsuarioId(Long idUsuario);

        List<Aluno> findByIdUsuarioStatus(Integer status);
}
