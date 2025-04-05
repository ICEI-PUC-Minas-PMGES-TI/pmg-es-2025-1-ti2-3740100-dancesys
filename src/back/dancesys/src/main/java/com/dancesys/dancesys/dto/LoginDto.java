package com.dancesys.dancesys.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class LoginDTO {
    private Long id;
    private String nome;
    public Integer tipo;
    private Boolean status;
    private String urlFoto;
}
