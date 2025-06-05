package com.dancesys.dancesys.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FigurinoAlunoFilter {
    private Long idAluno;
    private Long idFigurino;
    private Long idEvento;
    private Long idApresentacao;
    private Integer pagina;
    private Integer tamanho;
    private String orderBy;
    private String order;
}
