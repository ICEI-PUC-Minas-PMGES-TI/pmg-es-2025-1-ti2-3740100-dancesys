package com.dancesys.dancesys.controller;

import com.dancesys.dancesys.entity.AulaExtra;
import com.dancesys.dancesys.dto.AulaExtraDTO;
import com.dancesys.dancesys.dto.AulaExtraFilter;
import com.dancesys.dancesys.service.AulaExtraService;
import com.dancesys.dancesys.infra.PaginatedResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("evento/aula-extra")
public class AulaExtraController {

    @Autowired
    AulaExtraService aulaExtraService;

    @PostMapping(value = {"", "alterar"})
    public ResponseEntity<AulaExtraDTO> salvar(@RequestBody AulaExtraDTO dto) throws Exception {
        final AulaExtraDTO salvo = aulaExtraService.salvar(dto);
        return ResponseEntity.ok(salvo);
    }

    @PostMapping(value = "buscar")
    public PaginatedResponse<AulaExtra> buscar(@RequestBody AulaExtraFilter filtro) {
        return aulaExtraService.buscar(filtro);
    }

    @DeleteMapping(value = "excluir/{id}")
    public void excluir(@PathVariable Long id) {
        aulaExtraService.deletar(id);
    }
}