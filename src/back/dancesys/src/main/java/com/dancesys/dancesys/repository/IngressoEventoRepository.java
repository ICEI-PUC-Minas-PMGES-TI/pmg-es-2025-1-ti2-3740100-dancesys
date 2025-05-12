package com.dancesys.dancesys.repository;

import com.dancesys.dancesys.entity.IngressoEvento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngressoEventoRepository extends JpaRepository<IngressoEvento, Long> {
    IngressoEvento findById(long id);
}
