package com.dancesys.dancesys.repository;

import com.dancesys.dancesys.entity.ApresentacaoEvento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface ApresentacaoEventoRepository extends JpaRepository<ApresentacaoEvento,Long> {
    ApresentacaoEvento findById(long id);

    boolean existsByIdEvento_Id(Long id);
}
