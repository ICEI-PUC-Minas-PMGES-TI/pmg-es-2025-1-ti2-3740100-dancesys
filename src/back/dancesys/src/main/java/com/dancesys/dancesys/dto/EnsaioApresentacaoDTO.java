package com.dancesys.dancesys.dto;

import com.dancesys.dancesys.entity.ApresentacaoEvento;
import com.dancesys.dancesys.entity.Professor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class EnsaioApresentacaoDTO {
    private Long id;
    private LocalDateTime dataHoraInicio;
    private LocalDateTime dataHoraFim;
    private Long idProfessor;
    private Long idApresentacaoEvento;
    private List<Long> alunos;
}