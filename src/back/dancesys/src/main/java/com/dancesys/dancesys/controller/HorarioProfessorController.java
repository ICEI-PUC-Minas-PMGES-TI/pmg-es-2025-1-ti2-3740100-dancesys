package com.dancesys.dancesys.controller;

import com.dancesys.dancesys.dto.HorarioProfessorDTO;
import com.dancesys.dancesys.service.HorarioProfessorService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Getter
@Setter
@RestController
@RequestMapping("/horario")
public class HorarioProfessorController {
    @Autowired
    private HorarioProfessorService horarioProfessorService;

    @PostMapping(value = {"", "alterar"})
    public ResponseEntity<HorarioProfessorDTO> salvar(@RequestBody HorarioProfessorDTO dto){
        HorarioProfessorDTO hp = horarioProfessorService.salvar(dto);
        return ResponseEntity.ok(hp);
    }

    @DeleteMapping(value = "excluir/{id}")
    public String excluir(@PathVariable Long id){
        return horarioProfessorService.excluir(id);
    }
}
