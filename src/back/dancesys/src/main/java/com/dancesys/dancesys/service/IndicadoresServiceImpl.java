package com.dancesys.dancesys.service;

import com.dancesys.dancesys.dto.*;
import com.dancesys.dancesys.repository.AlunoRepository;
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
    private final AlunoRepository alunoRepository;

    public IndicadoresServiceImpl(
            DividendoRepository dividendoRepository,
            AulaExperimentalRepository aulaExperimentalRepository,
            AulaOcorrenciaRepository aulaOcorrenciaRepository,
            AlunoRepository alunoRepository
    ) {
        this.dividendoRepository = dividendoRepository;
        this.aulaExperimentalRepository = aulaExperimentalRepository;
        this.aulaOcorrenciaRepository = aulaOcorrenciaRepository;
        this.alunoRepository = alunoRepository;
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
        List<Object[]> result = aulaOcorrenciaRepository.getRelatorioAulas(idProfessor, ano);
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

    @Override
    public List<IndicadorAulasModalidadeDTO> getRelatorioAulasModalidade(Integer ano){
        List<Object[]> result = aulaOcorrenciaRepository.getRelatorioAulasModalidade(ano);
        List<IndicadorAulasModalidadeDTO> dtos = new ArrayList<>();

        for(Object[] r : result){
            IndicadorAulasModalidadeDTO dto = new IndicadorAulasModalidadeDTO();
            dto.setModalidade((String) r[0]);
            dto.setMes((Integer)r[1]);
            dto.setTotalAulas((Integer)r[2]);

            dtos.add(dto);
        }

        return dtos;
    }

    @Override
    public List<IndicadorAlunoModalideDTO> getRelatorioAlunoModalidade(){
        List<Object[]> result = alunoRepository.getRelatorioAlunoModalidade();
        List<IndicadorAlunoModalideDTO> dtos = new ArrayList<>();

        for(Object[] r : result){
            IndicadorAlunoModalideDTO dto = new IndicadorAlunoModalideDTO();
            dto.setModalidade((String) r[0]);
            dto.setNivel((Integer) r[1]);
            dto.setQuantidadeAlunos((Integer) r[2]);

            dtos.add(dto);
        }

        return dtos;
    }
}
