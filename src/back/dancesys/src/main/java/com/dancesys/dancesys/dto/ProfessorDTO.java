package com.dancesys.dancesys.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class ProfessorDTO {
    private Long id;
    private String nome;
    private String cpf;
    private String numero;
    private String email;
    private String senha;
    public Integer tipo;
    private Boolean status;
    private String endereco;
    private String urlFoto;
    private LocalDate dataNascimento;
    private LocalDate criadoEm;
    private String informacoesProfissionais;
    private BigDecimal valorHoraExtra;
}
