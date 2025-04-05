package com.dancesys.dancesys.repository;

import com.dancesys.dancesys.entity.Modalidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ModalidadeRepository extends JpaRepository<Modalidade,Long> {
}
