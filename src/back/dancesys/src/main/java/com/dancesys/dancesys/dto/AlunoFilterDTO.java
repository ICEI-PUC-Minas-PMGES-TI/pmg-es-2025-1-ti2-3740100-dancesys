package com.dancesys.dancesys.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AlunoFilterDTO {
    private String nome;
    private String email;
    private String cpf;
    private Integer tipo;
    private Boolean status;
}
