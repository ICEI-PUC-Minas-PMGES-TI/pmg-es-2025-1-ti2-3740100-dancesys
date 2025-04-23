package com.dancesys.dancesys.service;

import com.dancesys.dancesys.dto.AulaDTO;

public interface AulaService {
    public AulaDTO salvar(AulaDTO dto) throws Exception;

    public String mudarStatus(Long id) throws Exception;
}
