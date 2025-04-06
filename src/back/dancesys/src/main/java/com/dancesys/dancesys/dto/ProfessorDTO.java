package com.dancesys.dancesys.dto;

import com.dancesys.dancesys.entity.Usuario;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

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
    private Usuario idUsuario;
    private List<ProfessorModalidadeDTO> modalidades;
    private List<ProfessorModalidadeDTO> dmodalidades;
}
