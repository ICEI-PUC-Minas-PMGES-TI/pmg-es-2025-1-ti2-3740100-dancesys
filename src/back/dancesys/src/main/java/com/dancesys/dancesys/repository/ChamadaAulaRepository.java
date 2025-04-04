package com.dancesys.dancesys.repository;

import com.dancesys.dancesys.entity.ChamadaAula;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChamadaAulaRepository extends JpaRepository<ChamadaAula, Long> {

}
