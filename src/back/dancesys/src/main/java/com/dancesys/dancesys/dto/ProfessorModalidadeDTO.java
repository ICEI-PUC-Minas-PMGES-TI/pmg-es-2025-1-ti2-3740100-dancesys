package com.dancesys.dancesys.dto;

import com.dancesys.dancesys.entity.IdsCompostos.ProfessorModalidadeId;
import com.dancesys.dancesys.entity.Modalidade;
import com.dancesys.dancesys.entity.Professor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfessorModalidadeDTO {
    private ProfessorModalidadeId id;
    private Long idProfessor;
    private Long idModalidade;
    private Professor professor;
    private Modalidade modalidade;
}
