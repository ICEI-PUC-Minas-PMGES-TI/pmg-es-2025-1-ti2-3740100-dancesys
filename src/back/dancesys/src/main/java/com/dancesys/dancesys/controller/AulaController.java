package com.dancesys.dancesys.controller;

import com.dancesys.dancesys.dto.AulaDto;
import com.dancesys.dancesys.entity.Aula;
import com.dancesys.dancesys.service.AulaService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Getter
@Setter
@RestController
@RequestMapping("/aula")
public class AulaController {
    @Autowired
    AulaService aulaService;

    @PostMapping(value = "/criar")
    public ResponseEntity<AulaDto> salvar(@RequestBody AulaDto aula) throws Exception {
        final AulaDto salvo = aulaService.salvar(aula);
        return ResponseEntity.ok(salvo);
    }

    @DeleteMapping(value = "/excluir/{id}")
    public ResponseEntity<Aula> excluir(@PathVariable Long id) throws Exception {
        return aulaService.excluir(id);
    }
    @GetMapping(value = "/teste")
    public List<Aula> findAllAulas() {
        return aulaService.findAllAulas();
    }
}
