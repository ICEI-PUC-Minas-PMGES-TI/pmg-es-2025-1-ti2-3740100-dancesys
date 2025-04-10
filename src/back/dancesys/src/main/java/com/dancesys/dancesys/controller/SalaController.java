package com.dancesys.dancesys.controller;

import com.dancesys.dancesys.dto.ModalidadeDTO;
import com.dancesys.dancesys.dto.SalaDTO;
import com.dancesys.dancesys.entity.Modalidade;
import com.dancesys.dancesys.entity.Sala;
import com.dancesys.dancesys.service.ModalidadeService;
import com.dancesys.dancesys.service.SalaService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Getter
@Setter
@RestController
@RequestMapping("/sala")
public class SalaController {
    @Autowired
    private SalaService salaService;

    @PostMapping(value = {"", "alterar"})
    public ResponseEntity<SalaDTO> salvar(@RequestBody SalaDTO dto) throws Exception {
        SalaDTO salvo = salaService.salvar(dto);
        return ResponseEntity.ok(salvo);
    }

    @GetMapping(value = "buscar")
    public List<Sala> buscar() throws Exception {
        return salaService.buscar();
    }

    @DeleteMapping(value = "excluir/{id}")
    public String excluir(@PathVariable Long id) throws Exception {
        return salaService.excluir(id);
    }
}
