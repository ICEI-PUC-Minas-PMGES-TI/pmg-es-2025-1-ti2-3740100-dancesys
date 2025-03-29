package com.dancesys.dancesys.dto;

import com.dancesys.dancesys.infra.dto.BaseDto;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class UsuarioDto extends BaseDto {
    private Long id;

    private String nome;

    private String cpf;

    private String numero;

    private String email;

    private String senha;

    private Integer enumTipo;

    private Integer creditos;

    private Integer status;

    private String urlFoto;

    private LocalDate dataNascimento;

    private LocalDate criadoEm;

    private BigDecimal valorHoraExtra;

}

