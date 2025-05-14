package com.dancesys.dancesys.dto;

import com.dancesys.dancesys.entity.Evento;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter

public class ApresentacaoEventoDTO {
    private Long id;
    private LocalTime horaInicio;
    private LocalTime horaFim;
    private Long idEvento;
}
