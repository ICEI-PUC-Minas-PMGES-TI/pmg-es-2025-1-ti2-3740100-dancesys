package com.dancesys.dancesys.service;

import com.dancesys.dancesys.entity.Chamada;

public interface ChamadaService {
    public Chamada adicionarAluno(Long idAulaOcorrencia, Long idAluno) throws RuntimeException;

    public String removerAluno(Long idAluno, Long idAulaOcorrencia);
}
