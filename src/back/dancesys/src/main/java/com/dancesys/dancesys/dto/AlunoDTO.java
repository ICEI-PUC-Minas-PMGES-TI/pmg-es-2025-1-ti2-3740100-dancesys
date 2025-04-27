package com.dancesys.dancesys.dto;

import com.dancesys.dancesys.entity.Usuario;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
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
    private Integer status;
    private String endereco;
    private String urlFoto;
    private LocalDate dataNascimento;
    private LocalDate criadoEm;
    private Integer creditos;
    private Integer boolBaile;
    private Integer tipoAluno;
    private Usuario usuario;
    private List<ModalidadeAlunoNivelDTO> modalidades = new ArrayList<>();
}
