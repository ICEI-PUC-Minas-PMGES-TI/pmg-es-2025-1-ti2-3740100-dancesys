package com.dancesys.dancesys.service;

import com.dancesys.dancesys.dto.FigurinoApresentacaoDTO;

import java.util.List;

public interface FigurinoApresentacaoService {
    public FigurinoApresentacaoDTO salvar(FigurinoApresentacaoDTO dto) throws Exception;

    public List<FigurinoApresentacaoDTO> buscar();

    public void deletar (Long id);
}