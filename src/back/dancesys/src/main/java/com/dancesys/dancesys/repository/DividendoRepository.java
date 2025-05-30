package com.dancesys.dancesys.repository;


import com.dancesys.dancesys.entity.Dividendo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface DividendoRepository extends JpaRepository<Dividendo, Long> {
    Optional<Dividendo> findById(Long id);

    List<Dividendo> findByCriadoEmLessThanEqualAndStatusEquals(LocalDate criadoEm, Integer status);
}
