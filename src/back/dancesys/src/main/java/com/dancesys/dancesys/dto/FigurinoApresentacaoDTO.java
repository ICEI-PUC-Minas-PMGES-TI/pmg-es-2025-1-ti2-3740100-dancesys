package com.dancesys.dancesys.dto;

import com.dancesys.dancesys.entity.Aluno;
import com.dancesys.dancesys.entity.ApresentacaoEvento;
import com.dancesys.dancesys.entity.Figurino;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FigurinoApresentacaoDTO {
    private Long id;
    private Integer status;
    private String tamanho;
    private String codigo;
    private Figurino idFigurino;
    private Aluno idAluno;
    private ApresentacaoEvento idApresentacaoEvento;
}