package com.dancesys.dancesys.service;

import com.dancesys.dancesys.dto.AulaOcorrenciaDto;
import com.dancesys.dancesys.dto.ChamadaAulaDto;
import com.dancesys.dancesys.entity.ChamadaAula;

import java.util.List;

public interface AulaOcorrenciaService {
    public AulaOcorrenciaDto salvar(AulaOcorrenciaDto dto) throws Exception;

    public AulaOcorrenciaDto cancelar(AulaOcorrenciaDto dto) throws Exception;

    public List<ChamadaAulaDto> gerarChamadaAula(Long id) throws Exception;

}
