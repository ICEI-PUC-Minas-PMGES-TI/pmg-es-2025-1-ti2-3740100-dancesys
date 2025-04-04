package com.dancesys.dancesys.dto;

import com.dancesys.dancesys.entity.Usuario;
import com.dancesys.dancesys.infra.dto.BaseDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExperienciaAlunoModalidadeDto extends BaseDto {
        private Long id;
        private Integer nivel;
        private Usuario idAluno;
}
