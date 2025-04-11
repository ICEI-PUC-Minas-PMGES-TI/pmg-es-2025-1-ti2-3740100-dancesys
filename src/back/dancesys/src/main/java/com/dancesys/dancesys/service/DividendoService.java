package com.dancesys.dancesys.service;

import com.dancesys.dancesys.dto.DividendoDTO;

import java.util.List;

public interface DividendoService {
    public DividendoDTO salvar(DividendoDTO dto) throws Exception;

    public List<DividendoDTO> buscar();

    public String deletar(Long id);

}
