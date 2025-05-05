package com.dancesys.dancesys.controller;

import com.dancesys.dancesys.dto.EventoDTO;
import com.dancesys.dancesys.service.EventoService;
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
@RequestMapping("/evento")
public class EventoController {
    @Autowired
    EventoService eventoService;

    @PostMapping(value = {"","alterar"})
    public ResponseEntity<EventoDTO> salvar(@RequestBody EventoDTO dto) throws IOException {
        EventoDTO salvo = eventoService.salvar(dto);
        return ResponseEntity.ok(salvo);
    }
}
