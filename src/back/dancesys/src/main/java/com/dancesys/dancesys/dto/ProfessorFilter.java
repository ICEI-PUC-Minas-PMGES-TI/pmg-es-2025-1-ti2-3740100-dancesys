package com.dancesys.dancesys.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfessorFilter {
    private String nome;
    private String cpf;
    private String email;
    private Integer status;
    private Integer tamanho;
    private Integer pagina;
}
