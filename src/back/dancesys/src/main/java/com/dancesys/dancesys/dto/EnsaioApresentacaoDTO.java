package com.dancesys.dancesys.dto;

import com.dancesys.dancesys.entity.Professor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class EnsaioApresentacaoDTO {
    private Long id;
    private LocalDateTime dataHoraInicio;
    private LocalDateTime dataHoraFim;
    private Professor idProfessor;
}