package com.dancesys.dancesys.service;

import com.dancesys.dancesys.dto.FigurinoDTO;

import java.util.List;

public interface FigurinoService {
    public FigurinoDTO salvar(FigurinoDTO dto) throws Exception;

    public List<FigurinoDTO> buscar();

    public void deletar (Long id);
}