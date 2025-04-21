package com.dancesys.dancesys.controller;

import com.dancesys.dancesys.dto.AulaDTO;
import com.dancesys.dancesys.service.AulaService;
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

    @PostMapping(value = {"", "alterar"})
    public ResponseEntity<AulaDTO> salvar(@RequestBody AulaDTO dto) throws Exception {
        AulaDTO salvo = aulaService.salvar(dto);
        return ResponseEntity.ok(salvo);
    }

}
