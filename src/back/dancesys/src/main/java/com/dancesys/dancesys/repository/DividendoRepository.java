package com.dancesys.dancesys.repository;


import com.dancesys.dancesys.entity.Dividendo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DividendoRepository extends JpaRepository<Dividendo, Long> {
    Optional<Dividendo> findById(Long id);
}
