package com.dancesys.dancesys.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class EventoFilter {
    private String nome;
    private String local;
    private LocalDate data;
    private List<Long> alunos;
    private Integer pagina;
    private Integer tamanho;
    private String orderBy;
    private String order;
}
