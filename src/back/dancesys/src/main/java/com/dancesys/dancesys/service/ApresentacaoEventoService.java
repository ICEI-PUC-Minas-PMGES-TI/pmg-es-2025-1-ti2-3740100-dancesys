package com.dancesys.dancesys.service;

import com.dancesys.dancesys.dto.ApresentacaoEventoDTO;

import java.util.List;

public interface ApresentacaoEventoService {
    public ApresentacaoEventoDTO salvar(ApresentacaoEventoDTO dto) throws Exception;

    public List<ApresentacaoEventoDTO> buscar();

    public void deletar (Long id);
}
