package com.dancesys.dancesys.service;

import com.dancesys.dancesys.entity.Chamada;

public interface ChamadaService {
    public void adicionarAluno(Long idAulaOcorrencia, Long idAluno) throws RuntimeException;

    public void removerAluno(Long idAluno, Long idAulaOcorrencia);
}
