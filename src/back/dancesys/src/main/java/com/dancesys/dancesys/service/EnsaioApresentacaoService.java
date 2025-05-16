package com.dancesys.dancesys.service;

import com.dancesys.dancesys.dto.EnsaioApresentacaoDTO;
import com.dancesys.dancesys.dto.EnsaioFilter;
import com.dancesys.dancesys.entity.EnsaioApresentacao;
import com.dancesys.dancesys.infra.PaginatedResponse;

import java.util.List;

public interface EnsaioApresentacaoService {
    public EnsaioApresentacaoDTO salvar(EnsaioApresentacaoDTO dto) throws Exception;

    public PaginatedResponse<EnsaioApresentacao> buscar(EnsaioFilter filtro);

    public void deletar (Long id);
}