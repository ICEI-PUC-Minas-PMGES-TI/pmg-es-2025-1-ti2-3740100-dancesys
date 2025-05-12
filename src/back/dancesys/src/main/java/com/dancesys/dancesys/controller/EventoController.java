package com.dancesys.dancesys.controller;

import com.dancesys.dancesys.dto.EventoDTO;
import com.dancesys.dancesys.entity.Evento;
import com.dancesys.dancesys.service.EventoService;
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
@RequestMapping("/evento")
public class EventoController {
    @Autowired
    EventoService eventoService;

    @PostMapping(value = {"","alterar"})
    public ResponseEntity<EventoDTO> salvar(@RequestBody EventoDTO dto) throws IOException {
        EventoDTO salvo = eventoService.salvar(dto);
        return ResponseEntity.ok(salvo);
    }

    @GetMapping(value = "buscar")
    public List<Evento> buscar(){
        return eventoService.buscar();
    }
}
