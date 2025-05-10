package com.dancesys.dancesys.service;

import com.dancesys.dancesys.dto.EnsaioApresentacaoDTO;

import java.util.List;

public interface EnsaioApresentacaoService {
    public EnsaioApresentacaoDTO salvar(EnsaioApresentacaoDTO dto) throws Exception;

    public List<EnsaioApresentacaoDTO> buscar();
}