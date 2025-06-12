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

        @Query(value = """
            SELECT 
                m.nome AS nome_modalidade,
                man.nivel,
                COUNT(a.id) AS quantidade_alunos
            FROM 
                Modalidade_Aluno_Nivel man
            JOIN 
                Aluno a ON man.id_Aluno = a.id
            JOIN 
                Usuario u ON a.id_Usuario = u.id
            JOIN 
                Modalidade m ON man.id_Modalidade = m.id
            WHERE 
                u.status = 1
            GROUP BY 
                m.nome, man.nivel
            ORDER BY 
                m.nome, man.nivel;
        """, nativeQuery = true)
        public List<Object[]> getRelatorioAlunoModalidade();
}
