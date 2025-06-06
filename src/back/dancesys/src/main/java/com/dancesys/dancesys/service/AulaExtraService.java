package com.dancesys.dancesys.service;

import com.dancesys.dancesys.dto.AulaExtraDTO;
import com.dancesys.dancesys.dto.AulaExtraFilter;
import com.dancesys.dancesys.entity.AulaExtra;
import com.dancesys.dancesys.infra.PaginatedResponse;

public interface AulaExtraService {
    AulaExtraDTO salvar(AulaExtraDTO dto) throws RuntimeException;

    public PaginatedResponse<AulaExtra> buscar(AulaExtraFilter filtro);
}