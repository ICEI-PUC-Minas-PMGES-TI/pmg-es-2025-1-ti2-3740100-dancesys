package com.dancesys.dancesys.controller;

import com.dancesys.dancesys.dto.FigurinoAlunoFilter;
import com.dancesys.dancesys.dto.FigurinoApresentacaoDTO;
import com.dancesys.dancesys.entity.FigurinoApresentacao;
import com.dancesys.dancesys.infra.PaginatedResponse;
import com.dancesys.dancesys.service.FigurinoApresentacaoService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Getter
@Setter
@RestController
@RequestMapping("evento/figurino/aluno")
public class FigurinoApresentacaoController {
    @Autowired
    FigurinoApresentacaoService figurinoApresentacaoService;

    @PostMapping(value = {"", "alterar"})
    public ResponseEntity<FigurinoApresentacaoDTO> salvar(@RequestBody FigurinoApresentacaoDTO dto) throws Exception {
        final FigurinoApresentacaoDTO salvo = figurinoApresentacaoService.salvar(dto);
        return ResponseEntity.ok(salvo);
    }

    @PostMapping(value = "buscar")
    public PaginatedResponse<FigurinoApresentacao> buscar(@RequestBody FigurinoAlunoFilter filtro) {
        return figurinoApresentacaoService.buscar(filtro);
    }

    @DeleteMapping(value = "excluir/{id}")
    public void excluir(@PathVariable Long id) throws RuntimeException {
        figurinoApresentacaoService.deletar(id);
    }

    @GetMapping(value = "status/{id}")
    public void toggleStatus(@PathVariable Long id) throws RuntimeException {
        figurinoApresentacaoService.toggleStatus(id);
    }
}