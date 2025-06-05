package com.dancesys.dancesys.repository;

import com.dancesys.dancesys.entity.Figurino;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FigurinoRepository extends JpaRepository<Figurino, Long> {

    Optional<Figurino> findById(Long id);

}