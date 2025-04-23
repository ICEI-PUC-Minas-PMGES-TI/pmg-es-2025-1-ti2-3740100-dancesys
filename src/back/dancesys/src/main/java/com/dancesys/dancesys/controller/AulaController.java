package com.dancesys.dancesys.controller;

import com.dancesys.dancesys.dto.AulaDTO;
import com.dancesys.dancesys.entity.Chamada;
import com.dancesys.dancesys.service.AulaService;
import com.dancesys.dancesys.service.ChamadaService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Getter
@Setter
@RestController
@RequestMapping("/aula")
public class AulaController {
    @Autowired
    private AulaService aulaService;

    @Autowired
    private ChamadaService chamadaService;

    @PostMapping(value = {"", "alterar"})
    public ResponseEntity<AulaDTO> salvar(@RequestBody AulaDTO dto) throws Exception {
        AulaDTO salvo = aulaService.salvar(dto);
        return ResponseEntity.ok(salvo);
    }

    @GetMapping(value = "inscrever/{idAulaOcorrencia}/{idAluno}")
    public ResponseEntity<Chamada> adicionarAluno(@PathVariable Long idAulaOcorrencia, @PathVariable Long idAluno ) throws RuntimeException {
        Chamada chamada = chamadaService.adicionarAluno(idAulaOcorrencia, idAluno);
        return ResponseEntity.ok(chamada);
    }

    @GetMapping(value = "desinscrever/{idAulaOcorrencia}/{idAluno}")
    public String removerAluno(@PathVariable Long idAulaOcorrencia, @PathVariable Long idAluno ) throws RuntimeException {
        return chamadaService.removerAluno(idAluno,idAulaOcorrencia);
    }

    @GetMapping(value = "status/{id}")
    public String mudarStatus(@PathVariable Long id) throws Exception {
        return aulaService.mudarStatus(id);
    }

}
