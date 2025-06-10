package com.dancesys.dancesys.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class AulaExperimentalDTO {
    private Long id;
    private LocalDate data;
    private LocalTime horarioInicio;
    private LocalTime horarioFim;
    private String nome;
    private String cpf;
    private String numero;
    private Integer situacao;
    private Integer motivo;
    private String motivoOutro;
    private LocalDate criadoEm;
    private LocalDate finalizadoEm;
    private Long idProfessor;
}
