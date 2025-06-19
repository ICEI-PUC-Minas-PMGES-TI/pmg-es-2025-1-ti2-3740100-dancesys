package com.dancesys.dancesys.service;

import com.dancesys.dancesys.dto.ChamadaDTO;
import com.dancesys.dancesys.entity.Chamada;

import java.util.List;

public interface ChamadaService {
    public void adicionarAluno(Long idAulaOcorrencia, Long idAluno) throws RuntimeException;

    public void removerAluno(Long idAluno, Long idAulaOcorrencia);

    public void fazerChamada(List<ChamadaDTO> chamada, Long idAulaOcorrencia);
}
