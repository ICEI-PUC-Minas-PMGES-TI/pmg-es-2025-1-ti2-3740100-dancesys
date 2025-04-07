package com.dancesys.dancesys.repository;

import com.dancesys.dancesys.dto.AlunoFilterDTO;
import com.dancesys.dancesys.entity.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlunoRepository extends JpaRepository<Aluno, Integer> {


        @Query("SELECT a FROM Aluno a JOIN a.idUsuario u " +
                "WHERE (:nome IS NULL OR LOWER(u.nome) LIKE LOWER(CONCAT('%', :nome, '%'))) " +
                "AND (:cpf IS NULL OR u.cpf = :cpf) " +
                "AND (:email IS NULL OR LOWER(u.email) LIKE LOWER(CONCAT('%', :email, '%'))) " +
                "AND (:tipo IS NULL OR a.tipo = :tipo) " +
                "AND (:status IS NULL OR u.status = :status)")
        List<Aluno> buscarAlunos(
                @Param("nome") String nome,
                @Param("cpf") String cpf,
                @Param("email") String email,
                @Param("tipo") Integer tipo,
                @Param("status") Integer status
        );
}
