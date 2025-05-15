package com.dancesys.dancesys.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class EnsaioFilter {
    private Long idProfessor;
    private List<Long> apresentacoes;
    private List<Long> alunos;
    private LocalDate data;
    private Integer pagina;
    private Integer tamanho;
}
