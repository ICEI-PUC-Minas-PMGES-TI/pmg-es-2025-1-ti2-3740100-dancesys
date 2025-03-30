package com.dancesys.dancesys.dto;

import com.dancesys.dancesys.entity.Usuario;
import com.dancesys.dancesys.infra.dto.BaseDto;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
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

