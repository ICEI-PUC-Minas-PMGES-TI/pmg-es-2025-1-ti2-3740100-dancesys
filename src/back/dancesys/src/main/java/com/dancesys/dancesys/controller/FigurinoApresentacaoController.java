package com.dancesys.dancesys.controller;

import com.dancesys.dancesys.dto.FigurinoApresentacaoDTO;
import com.dancesys.dancesys.service.FigurinoApresentacaoService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Getter
@Setter
@RestController
@RequestMapping("/figurinoApresentacao")
public class FigurinoApresentacaoController {
    @Autowired
    FigurinoApresentacaoService figurinoApresentacaoService;

    @PostMapping(value = {"", "alterar"})
    public ResponseEntity<FigurinoApresentacaoDTO> salvar(@RequestBody FigurinoApresentacaoDTO dto) throws Exception {
        final FigurinoApresentacaoDTO salvo = figurinoApresentacaoService.salvar(dto);
        return ResponseEntity.ok(salvo);
    }

    @GetMapping(value = "buscar")
    public List<FigurinoApresentacaoDTO> buscar() {
        return figurinoApresentacaoService.buscar();
    }

    @DeleteMapping(value = "excluir/{id}")
    public void excluir(@PathVariable Long id) {
        figurinoApresentacaoService.deletar(id);
    }
}