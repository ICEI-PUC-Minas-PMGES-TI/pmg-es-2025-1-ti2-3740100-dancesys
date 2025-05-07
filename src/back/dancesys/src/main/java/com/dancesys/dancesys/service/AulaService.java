package com.dancesys.dancesys.service;

import com.dancesys.dancesys.dto.AulaDTO;
import com.dancesys.dancesys.dto.AulaFilter;
import com.dancesys.dancesys.entity.Aula;
import com.dancesys.dancesys.infra.PaginatedResponse;

public interface AulaService {
    public AulaDTO salvar(AulaDTO dto) throws Exception;

    public String mudarStatus(Long id) throws Exception;

    public void gerarAulasJobMensal() throws Exception;

    public PaginatedResponse<Aula> buscar(AulaFilter filter);
}
