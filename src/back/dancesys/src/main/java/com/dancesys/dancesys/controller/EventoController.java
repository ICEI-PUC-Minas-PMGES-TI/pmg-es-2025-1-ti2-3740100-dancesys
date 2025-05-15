package com.dancesys.dancesys.controller;

import com.dancesys.dancesys.dto.EventoDTO;
import com.dancesys.dancesys.dto.EventoFilter;
import com.dancesys.dancesys.entity.Evento;
import com.dancesys.dancesys.infra.PaginatedResponse;
import com.dancesys.dancesys.service.EventoService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Getter
@Setter
@RestController
@RequestMapping("/evento")
public class EventoController {
    @Autowired
    EventoService eventoService;

    @PostMapping(value = {"","alterar"})
    public ResponseEntity<EventoDTO> salvar(@RequestBody EventoDTO dto) throws IOException {
        EventoDTO salvo = eventoService.salvar(dto);
        return ResponseEntity.ok(salvo);
    }

    @PostMapping(value = "buscar")
    public PaginatedResponse<Evento> buscar(@RequestBody EventoFilter filtro){
        return eventoService.buscar(filtro);
    }

    @DeleteMapping(value = "excluir/{id}")
    public void excluir(@PathVariable Long id) throws RuntimeException {
        eventoService.excluir(idEvento);
    }
}
