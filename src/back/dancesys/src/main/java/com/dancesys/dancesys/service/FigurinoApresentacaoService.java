package com.dancesys.dancesys.service;

import com.dancesys.dancesys.dto.FigurinoAlunoFilter;
import com.dancesys.dancesys.dto.FigurinoApresentacaoDTO;
import com.dancesys.dancesys.entity.FigurinoApresentacao;
import com.dancesys.dancesys.infra.PaginatedResponse;

import java.util.List;

public interface FigurinoApresentacaoService {
    public FigurinoApresentacaoDTO salvar(FigurinoApresentacaoDTO dto) throws Exception;

    public PaginatedResponse<FigurinoApresentacao> buscar(FigurinoAlunoFilter filtro);

    public void deletar (Long id) throws RuntimeException;

    public void toggleStatus(Long id) throws RuntimeException;
}