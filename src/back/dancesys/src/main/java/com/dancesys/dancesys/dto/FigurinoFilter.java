package com.dancesys.dancesys.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class FigurinoFilter {
    private String nome;
    private BigDecimal valor;
    private Integer tipo;
    private Integer pagina;
    private Integer tamanho;
    private String orderBy;
    private String order;
}
