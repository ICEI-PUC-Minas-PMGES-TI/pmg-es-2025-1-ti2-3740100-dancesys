package com.dancesys.dancesys.dto;

import com.dancesys.dancesys.entity.Aluno;
import com.dancesys.dancesys.entity.IdsCompostos.AlunoModalidade;
import com.dancesys.dancesys.entity.Modalidade;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class ModalidadeAlunoNivelDTO {
    private AlunoModalidade id;
    private Integer nivel;
    private Aluno idAluno;
    private Modalidade idModalidade;
}
