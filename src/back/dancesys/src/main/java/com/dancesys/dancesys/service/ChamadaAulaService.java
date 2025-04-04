package com.dancesys.dancesys.service;

import com.dancesys.dancesys.dto.ChamadaAulaDto;

public interface ChamadaAulaService {
    public ChamadaAulaDto salvar(ChamadaAulaDto dto) throws Exception;

    public ChamadaAulaDto mudarStatus(ChamadaAulaDto dto) throws Exception;
}
