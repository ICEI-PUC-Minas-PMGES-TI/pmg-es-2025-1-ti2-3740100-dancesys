package com.dancesys.dancesys.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
public class AulaDTO {
    private Long id;
    private Integer diaSemana;
    private LocalTime horarioInicio;
    private LocalTime horarioFim;
    private Integer maxAlunos;
    private Integer nivel;
    private Integer status;
    private Long idSala;
    private Long idModalidade;
    private Long idProfessor;
    private List<Long> alunos;
}
