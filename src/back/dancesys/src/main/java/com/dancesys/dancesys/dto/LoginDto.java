package com.dancesys.dancesys.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginDto {
    private Long id;
    private Integer enumTipo;
    private Integer status;
    private String urlFoto;
}

