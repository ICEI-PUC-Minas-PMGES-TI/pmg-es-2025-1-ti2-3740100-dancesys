package com.dancesys.dancesys.controller;

import com.dancesys.dancesys.dto.*;
import com.dancesys.dancesys.entity.Dividendo;
import com.dancesys.dancesys.infra.PaginatedResponse;
import com.dancesys.dancesys.service.DividendoService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Getter
@Setter
@RestController
@RequestMapping("/dividendo")
public class DividendoController {
    @Autowired
    DividendoService dividendoService;

    @PostMapping(value = {"", "alterar"})
    public ResponseEntity<DividendoDTO> salvar(@RequestBody DividendoDTO dto) throws Exception {
        final DividendoDTO salvo = dividendoService.salvar(dto);
        return ResponseEntity.ok(salvo);
    }

    @DeleteMapping(value = "excluir/{id}")
    public String deletar(@PathVariable Long id){
        return dividendoService.deletar(id);
    }

    @PostMapping(value = "buscar")
    public PaginatedResponse<Dividendo> buscarTeste(@RequestBody DividendoFilter filtro){
        return dividendoService.buscar(filtro);
    }
}
