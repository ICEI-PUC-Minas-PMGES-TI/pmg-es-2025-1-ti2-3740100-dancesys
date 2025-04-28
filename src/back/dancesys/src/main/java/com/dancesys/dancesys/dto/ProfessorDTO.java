package com.dancesys.dancesys.dto;

import com.dancesys.dancesys.entity.Usuario;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class ProfessorDTO {
    private Long id;
    private String informacoesProfissionais;
    private BigDecimal valorHoraExtra;
    private Usuario usuario;
    private List<Long> modalidades;
}
