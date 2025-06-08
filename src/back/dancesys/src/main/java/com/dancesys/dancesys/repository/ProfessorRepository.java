package com.dancesys.dancesys.repository;

import com.dancesys.dancesys.entity.Professor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProfessorRepository extends JpaRepository<Professor,Long> {
    Professor findByIdUsuarioId(Long id);

    @Query("SELECT p FROM Professor p JOIN p.idUsuario u " +
            "WHERE (:nome IS NULL OR LOWER(u.nome) LIKE LOWER(CONCAT('%', :nome, '%'))) " +
            "AND (:cpf IS NULL OR LOWER(u.cpf) LIKE LOWER(CONCAT('%', :cpf ,'%'))) " +
            "AND (:email IS NULL OR LOWER(u.email) LIKE LOWER(CONCAT('%', :email, '%'))) " +
            "AND (:status IS NULL OR u.status = :status)")
    List<Professor> buscarProfessor(
            @Param("nome") String nome,
            @Param("cpf") String cpf,
            @Param("email") String email,
            @Param("status") Integer status
    );
}
