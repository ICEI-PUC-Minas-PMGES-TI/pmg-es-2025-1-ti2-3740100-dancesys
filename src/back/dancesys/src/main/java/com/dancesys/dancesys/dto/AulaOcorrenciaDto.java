package com.dancesys.dancesys.dto;

import com.dancesys.dancesys.entity.Aula;
import com.dancesys.dancesys.entity.Usuario;
import com.dancesys.dancesys.infra.dto.BaseDto;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class AulaOcorrenciaDto extends BaseDto {
    private Long id;
    private String codigoAula;
    private String listaIdsAlunos;
    private LocalDate data;
    private Integer status;
    private String motivoCancelamento;
    private Aula idAula;
}

