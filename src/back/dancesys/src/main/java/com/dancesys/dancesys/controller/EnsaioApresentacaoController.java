package com.dancesys.dancesys.controller;

import com.dancesys.dancesys.dto.EnsaioApresentacaoDTO;
import com.dancesys.dancesys.service.EnsaioApresentacaoService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Getter
@Setter
@RestController
@RequestMapping("/ensaioApresentacao")
public class EnsaioApresentacaoController {
    @Autowired
    EnsaioApresentacaoService ensaioApresentacaoService;

    @PostMapping(value = {"","alterar"})
    public ResponseEntity<EnsaioApresentacaoDTO> salvar(@RequestBody EnsaioApresentacaoDTO dto) throws Exception {
        final EnsaioApresentacaoDTO salvo = ensaioApresentacaoService.salvar(dto);
        return ResponseEntity.ok(salvo);
    }

    @GetMapping(value = "buscar")
    public List<EnsaioApresentacaoDTO> buscarEnsaioApresentacao() {
        return ensaioApresentacaoService.buscar();
    }

    @DeleteMapping(value = "excluir/{id}")
    public void excluir(@PathVariable Long id) {
        ensaioApresentacaoService.deletar(id);
    }
}