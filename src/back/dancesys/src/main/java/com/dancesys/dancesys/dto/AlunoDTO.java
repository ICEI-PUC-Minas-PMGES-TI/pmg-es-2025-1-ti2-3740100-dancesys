package com.dancesys.dancesys.dto;

import com.dancesys.dancesys.entity.Modalidade;
import com.dancesys.dancesys.entity.Usuario;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class AlunoDTO {
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
    private Integer creditos;
    private Boolean boolBaile;
    private Integer tipoAluno;
    private Usuario idUsuario;
    private List<Modalidade> modalidades;
    private List<Modalidade> dModalidades;
}
