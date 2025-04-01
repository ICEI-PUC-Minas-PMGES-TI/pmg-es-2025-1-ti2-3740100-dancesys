package com.dancesys.dancesys.controller;


import com.dancesys.dancesys.dto.AulaOcorrenciaDto;
import com.dancesys.dancesys.dto.ChamadaAulaDto;
import com.dancesys.dancesys.service.AulaOcorrenciaService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


//Arquivo para testes ate o job mensal ficar feito
@Getter
@Setter
@RestController
@RequestMapping("/ocorrencia")
public class AulaOcorrenciaController {
    @Autowired
    AulaOcorrenciaService aulaOcorrenciaService;

    @PostMapping(value = "/criar")
    public ResponseEntity<AulaOcorrenciaDto> salvar(@RequestBody AulaOcorrenciaDto aula) throws Exception {
        final AulaOcorrenciaDto salvo = aulaOcorrenciaService.salvar(aula);
        return ResponseEntity.ok(salvo);
    }

    @PostMapping(value = "/cancelar")
    public ResponseEntity<AulaOcorrenciaDto> cancelar(@RequestBody AulaOcorrenciaDto aula) throws Exception {
        final AulaOcorrenciaDto salvo = aulaOcorrenciaService.cancelar(aula);
        return ResponseEntity.ok(salvo);
    }

    @GetMapping(value = "/chamada/{id}")
    public List<ChamadaAulaDto> gerarChamada(@PathVariable Long id) throws Exception {
        return aulaOcorrenciaService.gerarChamadaAula(id);
    }
}
