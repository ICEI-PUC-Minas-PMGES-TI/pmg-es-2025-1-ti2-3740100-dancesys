package com.dancesys.dancesys.service;

import com.dancesys.dancesys.dto.IndicadorFinanceiroDTO;

import java.util.List;

public interface IndicadoresService {
    public List<IndicadorFinanceiroDTO> getRelatorioDividendo(Integer ano);
}
