package com.dancesys.dancesys.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class AulaExtraDTO {
    private Long id;
    private LocalDateTime horarioInicio;
    private LocalDateTime horarioFim;
    private Integer situacao;
    private String motivo;
    private String codigo;
    private Long idProfessor;
    private Long idSala;
    private Long idAluno;
}