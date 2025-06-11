package com.dancesys.dancesys.service;

import com.dancesys.dancesys.dto.IndicadorAulasDTO;
import com.dancesys.dancesys.dto.IndicadorConversaoDTO;
import com.dancesys.dancesys.dto.IndicadorFinanceiroDTO;
import com.dancesys.dancesys.repository.AulaExperimentalRepository;
import com.dancesys.dancesys.repository.AulaOcorrenciaRepository;
import com.dancesys.dancesys.repository.DividendoRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class IndicadoresServiceImpl implements IndicadoresService {
    private final DividendoRepository dividendoRepository;
    private final AulaExperimentalRepository  aulaExperimentalRepository;
    private final AulaOcorrenciaRepository aulaOcorrenciaRepository;

    public IndicadoresServiceImpl(
            DividendoRepository dividendoRepository,
            AulaExperimentalRepository aulaExperimentalRepository,
            AulaOcorrenciaRepository aulaOcorrenciaRepository
    ) {
        this.dividendoRepository = dividendoRepository;
        this.aulaExperimentalRepository = aulaExperimentalRepository;
        this.aulaOcorrenciaRepository = aulaOcorrenciaRepository;
    }

    @Override
    public List<IndicadorFinanceiroDTO> getRelatorioDividendo(Integer ano) {
        List<Object[]> resultados = dividendoRepository.buscarRelatorioDividendo(ano);
        List<IndicadorFinanceiroDTO> dtos = new ArrayList<>();

        for (Object[] r : resultados) {
            IndicadorFinanceiroDTO dto = new IndicadorFinanceiroDTO();
            dto.setMes((Integer) r[0]);
            dto.setTipo((Integer) r[1]);
            dto.setBoletosPagosSemAtraso(((Number) r[2]).longValue());
            dto.setSomaValoresSemAtraso((BigDecimal) r[3]);
            dto.setBoletosPagosComAtraso(((Number) r[4]).longValue());
            dto.setSomaValoresComAtraso((BigDecimal) r[5]);
            dto.setMediaDiasAtraso(r[6] != null ? ((Number) r[6]).doubleValue() : null);
            dto.setBoletosNaoPagos(((Number) r[7]).longValue());

            dtos.add(dto);
        }

        return dtos;
    }

    @Override
    public List<IndicadorConversaoDTO> getRelatorioConversao(Integer ano){
        List<Object[]> resultados = aulaExperimentalRepository.buscarEstatisticasPorAno(ano);
        List<IndicadorConversaoDTO> dtos = new ArrayList<>();

        for (Object[] r : resultados) {
            IndicadorConversaoDTO dto = new IndicadorConversaoDTO();
            dto.setMes((Integer) r[0]);
            dto.setTotalConvertido(((Number) r[1]).longValue());
            dto.setTotalRecusado(((Number) r[2]).longValue());
            dto.setTotalCriadas(((Number) r[3]).longValue());
            dto.setTotalFinalizadas(((Number) r[4]).longValue());
            dto.setTotalInteresse(((Number) r[5]).longValue());
            dto.setTotalFinanceiro(((Number) r[6]).longValue());
            dto.setTotalOutro(((Number) r[7]).longValue());

            dtos.add(dto);
        }

        return dtos;
    }

    @Override
    public List<IndicadorAulasDTO> getRelatorioAula(Integer ano, Long idProfessor) {
        List<Object[]> result = aulaOcorrenciaRepository.getRelatrioAulas(idProfessor, ano);
        List<IndicadorAulasDTO> dtos = new ArrayList<>();

        for(Object[] r : result){
            IndicadorAulasDTO dto = new IndicadorAulasDTO();
            dto.setMes((Integer)r[0]);
            dto.setTotalAulasOcorrentesRealizadas((Integer)r[1]);
            dto.setTotalAulasOcorrentesCanceladas((Integer)r[2]);
            dto.setMinutosAulasOcorrentes((Integer)r[3]);
            dto.setTotalAulasExtrasRealizadas((Integer)r[4]);
            dto.setTotalAulasExtrasCanceladas((Integer)r[5]);
            dto.setMinutosAulasExtras((Integer)r[6]);
            dto.setTotalAulasExperimentais((Integer)r[7]);
            dto.setMinutosAulasExperimentais((Integer)r[8]);

            dtos.add(dto);
        }

        return dtos;
    }
}
