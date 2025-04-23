package com.dancesys.dancesys.repository;

import com.dancesys.dancesys.entity.Chamada;
import com.dancesys.dancesys.entity.IdsCompostos.ChamadaId;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChamadaRepository extends CrudRepository<Chamada, ChamadaId> {
    Chamada findByIdAluno_Id(Long idAluno);

    List<Chamada> findByIdAulaOcorrencia_Id(Long id);
}
