package com.dancesys.dancesys.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AlunoFilter {
    private String nome;
    private String cpf;
    private String email;
    private Integer tipo;
    private Integer status;
    private Integer tamanho;
    private Integer pagina;
    private String orderBy;
    private String order;
}
