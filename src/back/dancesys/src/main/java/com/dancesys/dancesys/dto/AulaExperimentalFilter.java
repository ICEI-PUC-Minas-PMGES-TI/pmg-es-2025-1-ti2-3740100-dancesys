package com.dancesys.dancesys.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class AulaExperimentalFilter {
    private LocalDate dataInicial;
    private LocalDate dataFinal;
    private String cpf;
    private Long idProfessor;
    private List<Integer> situacao;
    private List<Integer> motivos;
    private Integer pagina;
    private Integer tamanho;
    private String orderBy;
    private String order;
}
