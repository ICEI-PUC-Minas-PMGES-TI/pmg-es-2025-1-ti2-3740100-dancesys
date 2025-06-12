package com.dancesys.dancesys.controller;

import com.dancesys.dancesys.dto.IndicadorAulasDTO;
import com.dancesys.dancesys.dto.IndicadorAulasModalidadeDTO;
import com.dancesys.dancesys.dto.IndicadorConversaoDTO;
import com.dancesys.dancesys.dto.IndicadorFinanceiroDTO;
import com.dancesys.dancesys.service.IndicadoresService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Getter
@Setter
@RestController
@RequestMapping("/indicador")
public class IndicadoresController {
    @Autowired
    IndicadoresService indicadoresService;

    @GetMapping(value = "financeiro/{ano}")
    public List<IndicadorFinanceiroDTO> getIndicadorFinanceiro(@PathVariable Integer ano){
        return indicadoresService.getRelatorioDividendo(ano);
    }

    @GetMapping(value = "conversao/{ano}")
    public List<IndicadorConversaoDTO> getConversao(@PathVariable Integer ano){
        return indicadoresService.getRelatorioConversao(ano);
    }

    @GetMapping(value = "aulas/{ano}/{idProfessor}")
    public List<IndicadorAulasDTO> getAulas(@PathVariable Integer ano, @PathVariable Long idProfessor){
        return indicadoresService.getRelatorioAula(ano, idProfessor);
    }

    @GetMapping(value = "aulas/modalidade/{ano}")
    public List<IndicadorAulasModalidadeDTO> getAulasModalidade(@PathVariable Integer ano){
        return indicadoresService.getRelatorioAulasModalidade(ano);
    }
}
