package com.dancesys.dancesys.controller;

import com.dancesys.dancesys.dto.AulaExperimentalDTO;
import com.dancesys.dancesys.dto.AulaExperimentalFilter;
import com.dancesys.dancesys.dto.MensagemDTO;
import com.dancesys.dancesys.entity.AulaExperimental;
import com.dancesys.dancesys.infra.PaginatedResponse;
import com.dancesys.dancesys.service.AulaExperimentalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("aula/experimental")
public class AulaExperimentalController {
    @Autowired
    AulaExperimentalService aulaExperimentalService;

    @PostMapping(value = {"", "alterar"})
    public AulaExperimentalDTO salvar(@RequestBody AulaExperimentalDTO dto) throws RuntimeException {
        return aulaExperimentalService.salvar(dto);
    }

    @PostMapping(value = "buscar")
    public PaginatedResponse<AulaExperimental> buscar(@RequestBody AulaExperimentalFilter filtro){
        return aulaExperimentalService.buscar(filtro);
    }

    @GetMapping(value = "converter/{id}")
    public void converter(@PathVariable Long id) throws RuntimeException {
        aulaExperimentalService.converter(id);
    }

    @PostMapping(value = "rejeitar/{motivo}/{id}")
    public void rejeitar(@PathVariable Integer motivo, @PathVariable Long id, @RequestBody MensagemDTO msg) throws RuntimeException {
        aulaExperimentalService.rejeitar(motivo, id, msg);
    }
}
