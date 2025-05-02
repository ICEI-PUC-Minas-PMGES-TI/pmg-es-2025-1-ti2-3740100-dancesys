package com.dancesys.dancesys.controller;

import com.dancesys.dancesys.dto.*;

import com.dancesys.dancesys.entity.Aluno;
import com.dancesys.dancesys.entity.Professor;
import com.dancesys.dancesys.entity.Usuario;
import com.dancesys.dancesys.infra.PaginatedResponse;
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

    @PostMapping(value = {"aluno", "aluno/alterar"})
    public ResponseEntity<AlunoDTO> salvarAluno(@RequestBody AlunoDTO dto) throws Exception {
        final AlunoDTO aluno = usuarioService.salvarAluno(dto);
        return ResponseEntity.ok(aluno);
    }

    @PostMapping(value = {"professor", "professor/alterar"})
    public ResponseEntity<ProfessorDTO> salvarProfessor(@RequestBody ProfessorDTO dto) throws Exception {
        final ProfessorDTO professor = usuarioService.salvarProfessor(dto);
        return ResponseEntity.ok(professor);
    }

    @PostMapping(value = "aluno/buscar")
    public PaginatedResponse<Aluno> buscarAlunos(@RequestBody AlunoFilter filtro){
        return usuarioService.buscarAlunos(filtro);
    }

    @PostMapping(value = "professor/buscar")
    public PaginatedResponse<Professor> buscarProfessor(@RequestBody ProfessorFilter filtro){
        return usuarioService.buscarProfessor(filtro);
    }

    @GetMapping(value = "buscar")
    public List<Usuario> buscar(){
        return usuarioService.buscar();
    }

    @GetMapping(value = "status/{id}")
    public ResponseEntity<Usuario> alterarStatus(@PathVariable Long id){
        Usuario u = usuarioService.alterarStatus(id);
        return ResponseEntity.ok(u);
    }
}
