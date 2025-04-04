package com.dancesys.dancesys.dto;

import com.dancesys.dancesys.entity.ExperienciaAlunoModalidade;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class UsuarioAlunoDto {
    private Long id;
    private String nome;
    private String cpf;
    private String numero;
    private String email;
    private Integer enumTipo;
    private LocalDate dataNascimento;
    private String modalidades;
    private Integer experiencia;
    private Integer boolBaile;
    private String endereco;
    private List<ExperienciaAlunoModalidade> newExpericaMod;
    private List<ExperienciaAlunoModalidade> deleteExpericaMod;
}
