package com.dancesys.dancesys.dto;

import com.dancesys.dancesys.entity.Aluno;
import com.dancesys.dancesys.entity.Evento;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class IngressoEventoDTO {
    private Long id;
    private Integer tipo;
    private String codigo;
    private Integer quantidade;
    private Long idAluno;
    private Long idEvento;
}
