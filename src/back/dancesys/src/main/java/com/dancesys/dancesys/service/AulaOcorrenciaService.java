package com.dancesys.dancesys.service;

import com.dancesys.dancesys.dto.AulaOcorrenciaDto;

public interface AulaOcorrenciaService {
    public AulaOcorrenciaDto salvar(AulaOcorrenciaDto dto) throws Exception;

    public AulaOcorrenciaDto cancelar(AulaOcorrenciaDto dto) throws Exception;

}
