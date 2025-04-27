package com.dancesys.dancesys.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
public class HorarioProfessorDTO {
    private Long id;
    private Integer diaSemana;
    private LocalTime horarioEntrada;
    private LocalTime horarioSaida;
    private Long idProfessor;
}
