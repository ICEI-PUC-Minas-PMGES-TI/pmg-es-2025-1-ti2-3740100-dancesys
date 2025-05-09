package com.dancesys.dancesys.controller;

import com.dancesys.dancesys.dto.EnsaioApresentacaoDTO;
import com.dancesys.dancesys.service.EnsaioApresentacaoService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

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
}