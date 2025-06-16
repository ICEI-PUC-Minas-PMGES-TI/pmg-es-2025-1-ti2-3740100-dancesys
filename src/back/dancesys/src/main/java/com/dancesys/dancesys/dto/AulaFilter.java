package com.dancesys.dancesys.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AulaFilter {
    private List<Integer> dias;
    private List<Long> professores;
    private List<Long> modalidades;
    private Integer tamanho;
    private Integer pagina;
    private String orderBy;
    private String order;
}
