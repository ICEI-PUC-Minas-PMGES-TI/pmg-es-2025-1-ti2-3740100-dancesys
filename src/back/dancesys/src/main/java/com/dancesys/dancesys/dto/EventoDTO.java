package com.dancesys.dancesys.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class EventoDTO {
    private Long id;
    private String nome;
    private String local;
    private LocalDateTime dataHoraInicio;
    private LocalDateTime dataHoraFim;
    private BigDecimal valor;
    private String urlFoto;
}
