package com.dancesys.dancesys.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IndicadorConversaoDTO {
    private Integer mes;
    private Long totalConvertido;
    private Long totalRecusado;
    private Long totalCriadas;
    private Long totalFinalizadas;
    private Long totalInteresse;
    private Long totalFinanceiro;
    private Long totalOutro;
}
