package com.dancesys.dancesys.service;

import com.dancesys.dancesys.dto.DividendoDTO;
import com.dancesys.dancesys.dto.DividendoFilter;
import com.dancesys.dancesys.entity.Dividendo;
import com.dancesys.dancesys.infra.PaginatedResponse;

import java.util.List;

public interface DividendoService {
    public DividendoDTO salvar(DividendoDTO dto) throws Exception;

    public String deletar(Long id);

    public DividendoDTO pagar(Long id) throws Exception;

    public PaginatedResponse<Dividendo> buscar(DividendoFilter filtro);
}
