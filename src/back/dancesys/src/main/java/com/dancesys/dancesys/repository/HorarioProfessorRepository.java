package com.dancesys.dancesys.repository;

import com.dancesys.dancesys.entity.HorarioProfessor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HorarioProfessorRepository extends JpaRepository<HorarioProfessor,Long> {
}
