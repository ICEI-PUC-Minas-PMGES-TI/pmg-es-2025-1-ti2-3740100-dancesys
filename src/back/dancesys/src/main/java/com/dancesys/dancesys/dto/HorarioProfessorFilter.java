package com.dancesys.dancesys.dto;

import com.dancesys.dancesys.entity.HorarioProfessor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class HorarioProfessorFilter {
    private List<Integer> diasSemana;
    private List<Long> professores;
    private Integer pagina;
    private Integer tamanho;
    private String orderBy;
    private String order;
}
