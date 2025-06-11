package com.dancesys.dancesys.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IndicadorAulasDTO {
    private Integer mes;
    private Integer totalAulasOcorrentesRealizadas;
    private Integer totalAulasOcorrentesCanceladas;
    private Integer minutosAulasOcorrentes;
    private Integer totalAulasExtrasRealizadas;
    private Integer totalAulasExtrasCanceladas;
    private Integer minutosAulasExtras;
    private Integer totalAulasExperimentais;
    private Integer minutosAulasExperimentais;
}
