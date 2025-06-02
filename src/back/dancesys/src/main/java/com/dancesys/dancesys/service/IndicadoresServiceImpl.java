package com.dancesys.dancesys.service;

import com.dancesys.dancesys.dto.IndicadorFinanceiroDTO;
import com.dancesys.dancesys.repository.DividendoRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class IndicadoresServiceImpl implements IndicadoresService {
    private final DividendoRepository dividendoRepository;

    public IndicadoresServiceImpl(DividendoRepository dividendoRepository) {
        this.dividendoRepository = dividendoRepository;
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
}
