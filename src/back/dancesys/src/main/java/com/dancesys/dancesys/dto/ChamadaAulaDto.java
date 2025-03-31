package com.dancesys.dancesys.dto;

import com.dancesys.dancesys.entity.AulaOcorrencia;
import com.dancesys.dancesys.entity.Usuario;
import com.dancesys.dancesys.infra.dto.BaseDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChamadaAulaDto extends BaseDto {
    private Long id;
    private Integer presenca;
    private AulaOcorrencia idAulaOcorrencia;
    private Usuario idAluno;
}

