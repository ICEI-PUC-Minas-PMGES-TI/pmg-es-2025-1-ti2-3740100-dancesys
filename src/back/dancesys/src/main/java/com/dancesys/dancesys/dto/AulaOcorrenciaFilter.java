package com.dancesys.dancesys.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class AulaOcorrenciaFilter {
    private List<Long> professores;
    private List<Long> alunos;
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private String codigo;
    private Integer tamanho;
    private Integer pagina;
}
