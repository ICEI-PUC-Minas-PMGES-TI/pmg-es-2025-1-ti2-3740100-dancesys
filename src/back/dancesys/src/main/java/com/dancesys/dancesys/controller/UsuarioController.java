package com.dancesys.dancesys.controller;

import com.dancesys.dancesys.dto.LoginDto;
import com.dancesys.dancesys.dto.UsuarioDto;
import com.dancesys.dancesys.dto.UsuarioFilterDto;
import com.dancesys.dancesys.entity.Usuario;
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

    @PostMapping(value = "/register")
    public ResponseEntity<UsuarioDto> salvar(@RequestBody UsuarioDto usuario) throws Exception {
        final UsuarioDto salvo = usuarioService.salvar(usuario);
        return ResponseEntity.ok(salvo);
    }

    @PostMapping(value = "/auth")
    public LoginDto logar(@RequestBody UsuarioDto usuario) throws Exception {
        return usuarioService.login(usuario);
    }

    @GetMapping("/status/{id}")
    public ResponseEntity<UsuarioDto> buscarPorId(@PathVariable("id") Long id) throws Exception{
        final UsuarioDto salvo = usuarioService.mudarStatus(id);
        return ResponseEntity.ok(salvo);
    }

    @PostMapping(value = "/buscar")
    public List<Usuario> buscarUsarios(@RequestBody UsuarioFilterDto filtro) throws Exception {
        return usuarioService.buscarUsuarios(filtro);
    }
}
