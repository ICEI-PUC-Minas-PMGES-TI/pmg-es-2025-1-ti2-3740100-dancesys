package com.dancesys.dancesys.repository;

import com.dancesys.dancesys.entity.AulaExperimental;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AulaExperimentalRepository extends JpaRepository<AulaExperimental, Long> {
}
