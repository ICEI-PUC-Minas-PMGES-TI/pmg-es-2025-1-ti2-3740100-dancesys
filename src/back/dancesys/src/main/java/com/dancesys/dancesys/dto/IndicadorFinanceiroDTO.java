package com.dancesys.dancesys.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class IndicadorFinanceiroDTO {
    private Integer mes;
    private Integer tipo;
    private Long boletosPagosSemAtraso;
    private BigDecimal somaValoresSemAtraso;
    private Long boletosPagosComAtraso;
    private BigDecimal somaValoresComAtraso;
    private Double mediaDiasAtraso;
    private Long boletosNaoPagos;
}
