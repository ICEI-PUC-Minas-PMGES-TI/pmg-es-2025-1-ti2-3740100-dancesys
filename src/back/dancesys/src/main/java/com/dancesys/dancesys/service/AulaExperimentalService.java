package com.dancesys.dancesys.service;

import com.dancesys.dancesys.dto.AulaExperimentalDTO;
import com.dancesys.dancesys.dto.AulaExperimentalFilter;
import com.dancesys.dancesys.entity.AulaExperimental;
import com.dancesys.dancesys.infra.PaginatedResponse;

public interface AulaExperimentalService {
    public AulaExperimentalDTO salvar(AulaExperimentalDTO dto) throws RuntimeException;

    public PaginatedResponse<AulaExperimental> buscar(AulaExperimentalFilter filtro);
}
