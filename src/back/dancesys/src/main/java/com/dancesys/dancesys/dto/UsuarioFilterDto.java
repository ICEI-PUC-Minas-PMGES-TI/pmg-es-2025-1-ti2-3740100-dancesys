package com.dancesys.dancesys.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class UsuarioFilterDto {
    private List<Long> id;
    private List<Integer> enumTipo;
    private Integer status;
    private List<String> nome;
    private List<String> cpf;
    private LocalDate dataInicio;
    private LocalDate dataFim;
}
