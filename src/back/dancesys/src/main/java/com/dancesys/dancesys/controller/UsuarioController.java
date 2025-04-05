package com.dancesys.dancesys.controller;

import com.dancesys.dancesys.dto.AlunoDTO;
import com.dancesys.dancesys.dto.LoginDTO;
import com.dancesys.dancesys.dto.UsuarioDTO;

import com.dancesys.dancesys.service.UsuarioService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Getter
@Setter
@RestController
@RequestMapping("/usuario")
public class UsuarioController {
    @Autowired
    UsuarioService usuarioService;

    @PostMapping(value = {"", "alterar"})
    public ResponseEntity<UsuarioDTO> salvar(@RequestBody UsuarioDTO usuario) throws Exception {
        final UsuarioDTO salvo = usuarioService.salvar(usuario);
        return ResponseEntity.ok(salvo);
    }

    @PostMapping(value = "auth")
    public ResponseEntity<LoginDTO> login(@RequestBody UsuarioDTO usuario) throws Exception {
        final LoginDTO login = usuarioService.login(usuario);
        return ResponseEntity.ok(login);
    }

    @PostMapping(value = "aluno")
    public ResponseEntity<AlunoDTO> salvarAluno(@RequestBody AlunoDTO dto) throws Exception {
        final  AlunoDTO aluno = usuarioService.salvarAluno(dto);
        return ResponseEntity.ok(aluno);
    }
}
