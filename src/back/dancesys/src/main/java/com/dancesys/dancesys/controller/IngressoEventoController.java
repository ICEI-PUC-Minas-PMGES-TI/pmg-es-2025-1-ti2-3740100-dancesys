package com.dancesys.dancesys.controller;

import com.dancesys.dancesys.dto.IngressoEventoDTO;
import com.dancesys.dancesys.service.IngressoEventoService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Getter
@Setter
@RestController
@RequestMapping("evento/ingresso")
public class IngressoEventoController {
    @Autowired
    IngressoEventoService ingressoEventoService;

    @PostMapping(value = {"","alterar"})
    public ResponseEntity<IngressoEventoDTO> salvar(@RequestBody IngressoEventoDTO dto) throws Exception {
        final IngressoEventoDTO salvo = ingressoEventoService.salvar(dto);
        return ResponseEntity.ok(salvo);
    }

    @GetMapping(value = "buscar")
    public List<IngressoEventoDTO> buscarIngressoEvento() {
        return ingressoEventoService.buscar();
    }

    @DeleteMapping(value = "excluir/{id}")
    public void excluir(@PathVariable Long id) {
        ingressoEventoService.deletar(id);
    }
}