package com.dancesys.dancesys.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter public class DividendoFilter {
    private String criadoEm;
    private String pagoEm;
    private List<Long> alunos;
    private List<Integer> status;
    private List<Integer> tipos;
    private Integer tamanho;
    private Integer pagina;
}
