package com.dancesys.dancesys.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class AulaExperimentalDTO {
    private Long id;
    private LocalDateTime dataHorarioInicio;
    private LocalDateTime dataHorarioFim;
    private String cpf;
    private String numero;
    private Integer situacao;
    private Integer motivo;
    private String motivoOutro;
    private Long idProfessor;
}
