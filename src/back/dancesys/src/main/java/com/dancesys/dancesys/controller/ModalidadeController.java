package com.dancesys.dancesys.controller;

import com.dancesys.dancesys.dto.ModalidadeDTO;
import com.dancesys.dancesys.entity.Modalidade;
import com.dancesys.dancesys.service.ModalidadeService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Getter
@Setter
@RestController
@RequestMapping("/modalidade")
public class ModalidadeController {

    @Autowired
    private ModalidadeService modalidadeService;

    @PostMapping(value = {"", "alterar"})
    public ResponseEntity<ModalidadeDTO> salvar(@RequestBody ModalidadeDTO dto) throws Exception {
        ModalidadeDTO salvo = modalidadeService.salvar(dto);
        return ResponseEntity.ok(salvo);
    }

    @GetMapping(value = "buscar")
    public List<Modalidade> buscar(ModalidadeDTO dto) throws Exception {
        return modalidadeService.buscar();
    }

    @DeleteMapping(value = "excluir/{id}")
    public String excluir(@PathVariable Long id) throws Exception {
        return modalidadeService.excluir(id);
    }
}
