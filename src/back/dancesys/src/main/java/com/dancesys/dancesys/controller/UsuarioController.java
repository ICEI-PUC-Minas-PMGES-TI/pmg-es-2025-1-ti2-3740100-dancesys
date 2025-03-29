package com.dancesys.dancesys.controller;

import com.dancesys.dancesys.dto.UsuarioDto;
import com.dancesys.dancesys.entity.Usuario;
import com.dancesys.dancesys.service.UsuarioService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Getter
@Setter
@RestController
@RequestMapping("/usuario")
public class UsuarioController {
    @Autowired
    UsuarioService usuarioService;

    @PostMapping(value = "/register")
    public ResponseEntity<UsuarioDto> salvar(@RequestBody UsuarioDto usuario) throws Exception {
        final UsuarioDto salvo = usuarioService.salvar(usuario);
        return ResponseEntity.ok(salvo);
    }

    @PostMapping(value = "/auth")
    public Usuario logar(@RequestBody UsuarioDto usuario) throws Exception {
        return usuarioService.login(usuario);
    }

    @GetMapping("/status/{id}")
    public ResponseEntity<Usuario> buscarPorId(@PathVariable("id") Long id) throws Exception{
        final Usuario salvo = usuarioService.mudarStatus(id);
        return ResponseEntity.ok(salvo);
    }
}
