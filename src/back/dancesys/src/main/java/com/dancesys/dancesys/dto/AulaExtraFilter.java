package com.dancesys.dancesys.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class AulaExtraFilter {
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private Long idProfessor;
    private Long idAluno;
    private List<Integer> status;
    private Integer pagina;
    private Integer tamanho;
    private String orderBy;
    private String order;
}