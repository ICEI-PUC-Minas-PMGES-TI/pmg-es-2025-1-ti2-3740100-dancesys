package com.dancesys.dancesys.service;

import com.dancesys.dancesys.dto.FigurinoDTO;
import com.dancesys.dancesys.dto.FigurinoFilter;
import com.dancesys.dancesys.entity.Figurino;
import com.dancesys.dancesys.infra.PaginatedResponse;

import java.util.List;

public interface FigurinoService {
    public FigurinoDTO salvar(FigurinoDTO dto) throws Exception;

    public PaginatedResponse<Figurino> buscar(FigurinoFilter filtro);

    public void deletar (Long id);
}