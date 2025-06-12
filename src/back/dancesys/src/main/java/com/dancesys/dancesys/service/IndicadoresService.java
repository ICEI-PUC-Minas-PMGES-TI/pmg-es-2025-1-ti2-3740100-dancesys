package com.dancesys.dancesys.service;

import com.dancesys.dancesys.dto.*;

import java.util.List;

public interface IndicadoresService {
    public List<IndicadorFinanceiroDTO> getRelatorioDividendo(Integer ano);

    public List<IndicadorConversaoDTO> getRelatorioConversao(Integer ano);

    public List<IndicadorAulasDTO> getRelatorioAula(Integer ano, Long idProfessor);

    public List<IndicadorAulasModalidadeDTO> getRelatorioAulasModalidade(Integer ano);

    public List<IndicadorAlunoModalideDTO> getRelatorioAlunoModalidade();
}
