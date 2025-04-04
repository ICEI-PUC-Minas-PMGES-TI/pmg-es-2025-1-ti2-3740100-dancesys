package com.dancesys.dancesys.dto;

import com.dancesys.dancesys.entity.Usuario;
import com.dancesys.dancesys.infra.dto.BaseDto;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
public class AulaDto extends BaseDto {
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

