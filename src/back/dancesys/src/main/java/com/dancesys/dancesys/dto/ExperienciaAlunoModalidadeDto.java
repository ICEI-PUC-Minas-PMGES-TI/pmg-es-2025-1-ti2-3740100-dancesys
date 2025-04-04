package com.dancesys.dancesys.dto;

import com.dancesys.dancesys.entity.Usuario;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExperienciaAlunoModalidadeDto {
        private Long id;
        private Integer nivel;
        private Usuario idAluno;
}
