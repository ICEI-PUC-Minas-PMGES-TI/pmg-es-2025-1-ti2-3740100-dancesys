package com.dancesys.dancesys.controller;

import com.dancesys.dancesys.dto.MensagemDTO;
import com.dancesys.dancesys.entity.AulaExtra;
import com.dancesys.dancesys.dto.AulaExtraDTO;
import com.dancesys.dancesys.dto.AulaExtraFilter;
import com.dancesys.dancesys.service.AulaExtraService;
import com.dancesys.dancesys.infra.PaginatedResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("aula/extra")
public class AulaExtraController {

    @Autowired
    AulaExtraService aulaExtraService;

    @PostMapping(value = {"", "alterar"})
    public ResponseEntity<AulaExtraDTO> salvar(@RequestBody AulaExtraDTO dto) throws RuntimeException {
        final AulaExtraDTO salvo = aulaExtraService.salvar(dto);
        return ResponseEntity.ok(salvo);
    }

    @PostMapping(value = "buscar")
    public PaginatedResponse<AulaExtra> buscar(@RequestBody AulaExtraFilter filtro){
        return aulaExtraService.buscar(filtro);
    }

    @GetMapping(value = "aceitar/{id}/{idSala}")
    public void aceitar(@PathVariable Long id, @PathVariable Long idSala) throws RuntimeException {
        aulaExtraService.aceitar(id, idSala);
    }

    @PostMapping(value = "indeferir/{id}")
    public void indeferir(@PathVariable Long id, @RequestBody MensagemDTO msg) throws RuntimeException {
        aulaExtraService.indeferir(id, msg);
    }

    @PostMapping(value = "cancelar/{id}")
    public void cancelar(@PathVariable Long id, @RequestBody MensagemDTO msg) throws RuntimeException {
        aulaExtraService.cancelar(id, msg);
    }
}