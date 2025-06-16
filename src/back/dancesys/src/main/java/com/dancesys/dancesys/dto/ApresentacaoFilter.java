package com.dancesys.dancesys.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ApresentacaoFilter {
    private String nome;
    private Long idEvento;
    private List<Long> alunos;
    private Integer pagina;
    private Integer tamanho;
    private String orderBy;
    private String order;
}
