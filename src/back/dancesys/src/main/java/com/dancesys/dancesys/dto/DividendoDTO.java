package com.dancesys.dancesys.dto;


import com.dancesys.dancesys.entity.Aluno;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class DividendoDTO {
    private Long id;
    private BigDecimal valor;
    private LocalDate criadoEm;
    private LocalDate pagoEm;
    private Integer tipo;
    private Integer status;
    private String codigo;
    private Aluno idAluno;
    private Long mesesAtrasos;
}
