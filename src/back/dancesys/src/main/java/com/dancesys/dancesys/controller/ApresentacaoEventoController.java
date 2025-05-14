package com.dancesys.dancesys.controller;

import com.dancesys.dancesys.dto.ApresentacaoEventoDTO;
import com.dancesys.dancesys.dto.ApresentacaoFilter;
import com.dancesys.dancesys.entity.ApresentacaoEvento;
import com.dancesys.dancesys.infra.PaginatedResponse;
import com.dancesys.dancesys.service.ApresentacaoEventoService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Getter
@Setter
@RestController
@RequestMapping("/apresentacaoEvento")
public class ApresentacaoEventoController {
    @Autowired
    private ApresentacaoEventoService apresentacaoEventoService;

    @PostMapping(value = {"", "alterar"})
    public ResponseEntity<ApresentacaoEventoDTO> salvar(@RequestBody ApresentacaoEventoDTO dto) throws Exception {
        ApresentacaoEventoDTO salvo = apresentacaoEventoService.salvar(dto);
        return ResponseEntity.ok(salvo);
    }

    @DeleteMapping(value = "excluir/{id}")
    public void excluir(@PathVariable Long id) {
        apresentacaoEventoService.deletar(id);
    }

    @PostMapping(value = "buscar")
    public PaginatedResponse<ApresentacaoEvento> buscar(@RequestBody ApresentacaoFilter filtro) {
        return apresentacaoEventoService.buscar(filtro);
    }
}
