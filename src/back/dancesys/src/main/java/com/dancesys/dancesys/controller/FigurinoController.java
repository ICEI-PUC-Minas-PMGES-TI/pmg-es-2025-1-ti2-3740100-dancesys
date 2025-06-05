package com.dancesys.dancesys.controller;

import com.dancesys.dancesys.dto.FigurinoDTO;
import com.dancesys.dancesys.dto.FigurinoFilter;
import com.dancesys.dancesys.entity.Figurino;
import com.dancesys.dancesys.infra.PaginatedResponse;
import com.dancesys.dancesys.service.FigurinoService;
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
@RequestMapping("evento/figurino")
public class FigurinoController {
    @Autowired
    FigurinoService figurinoService;

    @PostMapping(value = {"", "alterar"})
    public ResponseEntity<FigurinoDTO> salvar(@RequestBody FigurinoDTO dto) throws Exception {
        final FigurinoDTO salvo = figurinoService.salvar(dto);
        return ResponseEntity.ok(salvo);
    }

    @PostMapping(value = "buscar")
    public PaginatedResponse<Figurino> buscar(@RequestBody FigurinoFilter filtro) {
        return figurinoService.buscar(filtro);
    }

    @DeleteMapping(value = "excluir/{id}")
    public void excluir(@PathVariable Long id) {
        figurinoService.deletar(id);
    }
}
