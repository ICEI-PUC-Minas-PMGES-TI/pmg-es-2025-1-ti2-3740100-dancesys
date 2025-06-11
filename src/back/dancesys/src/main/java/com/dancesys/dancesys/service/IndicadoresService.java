package com.dancesys.dancesys.service;

import com.dancesys.dancesys.dto.IndicadorAulasDTO;
import com.dancesys.dancesys.dto.IndicadorConversaoDTO;
import com.dancesys.dancesys.dto.IndicadorFinanceiroDTO;

import java.util.List;

public interface IndicadoresService {
    public List<IndicadorFinanceiroDTO> getRelatorioDividendo(Integer ano);

    public List<IndicadorConversaoDTO> getRelatorioConversao(Integer ano);

    public List<IndicadorAulasDTO> getRelatorioAula(Integer ano, Long idProfessor);
}
