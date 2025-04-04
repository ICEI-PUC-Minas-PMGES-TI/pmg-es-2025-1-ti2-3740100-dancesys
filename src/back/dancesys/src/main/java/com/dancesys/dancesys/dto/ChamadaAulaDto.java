package com.dancesys.dancesys.dto;

import com.dancesys.dancesys.entity.AulaOcorrencia;
import com.dancesys.dancesys.entity.Usuario;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChamadaAulaDto {
    private Long id;
    private Integer presenca;
    private AulaOcorrencia idAulaOcorrencia;
    private Usuario idAluno;
}

