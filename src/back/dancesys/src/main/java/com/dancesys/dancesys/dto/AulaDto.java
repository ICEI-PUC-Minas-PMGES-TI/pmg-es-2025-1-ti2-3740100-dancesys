package com.dancesys.dancesys.dto;

import com.dancesys.dancesys.entity.Usuario;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
public class AulaDto {
    private Long id;
    private Integer tipo;
    private LocalTime horarioInicio;
    private LocalTime horarioFim;
    private Integer diaSemana;
    private String listaIdsAlunos;
    private Usuario idProfessor;
    private String sala;
    private Integer maxAlunos;
    private Integer status;
}

