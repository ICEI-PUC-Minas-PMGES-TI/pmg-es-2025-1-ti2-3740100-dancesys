package com.dancesys.dancesys.controller;

import com.dancesys.dancesys.dto.*;
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

    @GetMapping(value = "buscar")
    public List<DividendoDTO> buscar(){
        return dividendoService.buscar();
    }

    @DeleteMapping(value = "excluir/{id}")
    public String deletar(@PathVariable Long id){
        return dividendoService.deletar(id);
    }

//    @PostMapping(value = "auth")
//    public ResponseEntity<LoginDTO> login(@RequestBody UsuarioDTO usuario) throws Exception {
//        final LoginDTO login = usuarioService.login(usuario);
//        return ResponseEntity.ok(login);
//    }
//
//    @PostMapping(value = "aluno")
//    public ResponseEntity<AlunoDTO> salvarAluno(@RequestBody AlunoDTO dto) throws Exception {
//        final AlunoDTO aluno = usuarioService.salvarAluno(dto);
//        return ResponseEntity.ok(aluno);
//    }
//
//    @PostMapping(value = "professor")
//    public ResponseEntity<ProfessorDTO> salvarProfessor(@RequestBody ProfessorDTO dto) throws Exception {
//        final ProfessorDTO professor = usuarioService.salvarProfessor(dto);
//        return ResponseEntity.ok(professor);
//    }
//
//    @GetMapping("aluno/buscar")
//    public ResponseEntity<List<AlunoDTO>> buscarAlunos(
//            @RequestParam(required = false) String nome,
//            @RequestParam(required = false) String cpf,
//            @RequestParam(required = false) String email,
//            @RequestParam(required = false) Integer tipo,
//            @RequestParam(required = false) Integer status) {
//
//        List<AlunoDTO> alunos = usuarioService.buscarAlunos(nome, cpf, email, tipo, status);
//        return ResponseEntity.ok(alunos);
//    }
//
//    @GetMapping(value = "buscar")
//    public List<Usuario> buscar(){
//        return usuarioService.buscar();
//    }
}
