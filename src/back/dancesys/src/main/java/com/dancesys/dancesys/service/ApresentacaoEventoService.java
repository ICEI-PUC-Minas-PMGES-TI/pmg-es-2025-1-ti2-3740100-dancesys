package com.dancesys.dancesys.service;

import com.dancesys.dancesys.dto.ApresentacaoEventoDTO;
import com.dancesys.dancesys.dto.ApresentacaoFilter;
import com.dancesys.dancesys.entity.ApresentacaoEvento;
import com.dancesys.dancesys.infra.PaginatedResponse;

import java.util.List;

public interface ApresentacaoEventoService {
    public ApresentacaoEventoDTO salvar(ApresentacaoEventoDTO dto) throws Exception;

    public PaginatedResponse<ApresentacaoEvento> buscar(ApresentacaoFilter filtro);

    public void deletar (Long id);
}
