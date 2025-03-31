package com.dancesys.dancesys.repository;

import com.dancesys.dancesys.entity.ChamadaAula;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChamadaAulaRepository extends CrudRepository<ChamadaAula, Long> {

}
