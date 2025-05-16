package com.dancesys.dancesys.controller;

import com.dancesys.dancesys.dto.AulaDTO;
import com.dancesys.dancesys.dto.AulaFilter;
import com.dancesys.dancesys.dto.AulaOcorrenciaFilter;
import com.dancesys.dancesys.dto.MensagemDTO;
import com.dancesys.dancesys.entity.Aula;
import com.dancesys.dancesys.entity.AulaOcorrencia;
import com.dancesys.dancesys.entity.Chamada;
import com.dancesys.dancesys.infra.PaginatedResponse;
import com.dancesys.dancesys.service.AulaService;
import com.dancesys.dancesys.service.ChamadaService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Getter
@Setter
@RestController
@RequestMapping("/aula")
public class AulaController {
    @Autowired
    private AulaService aulaService;

    @Autowired
    private ChamadaService chamadaService;

    @PostMapping(value = {"", "alterar"})
    public ResponseEntity<AulaDTO> salvar(@RequestBody AulaDTO dto) throws Exception {
        AulaDTO salvo = aulaService.salvar(dto);
        return ResponseEntity.ok(salvo);
    }

    @PostMapping(value = "buscar")
    public PaginatedResponse<Aula> buscar(@RequestBody AulaFilter filter){
        return aulaService.buscar(filter);
    }

    @GetMapping(value = "inscrever/{idAulaOcorrencia}/{idAluno}")
    public ResponseEntity<Chamada> adicionarAluno(@PathVariable Long idAulaOcorrencia, @PathVariable Long idAluno ) throws RuntimeException {
        Chamada chamada = chamadaService.adicionarAluno(idAulaOcorrencia, idAluno);
        return ResponseEntity.ok(chamada);
    }

    @GetMapping(value = "desinscrever/{idAulaOcorrencia}/{idAluno}")
    public String removerAluno(@PathVariable Long idAulaOcorrencia, @PathVariable Long idAluno ) throws RuntimeException {
        return chamadaService.removerAluno(idAluno,idAulaOcorrencia);
    }

    @GetMapping(value = "status/{id}")
    public void mudarStatus(@PathVariable Long id) throws Exception {
        aulaService.mudarStatus(id);
    }

    @PostMapping(value = "ocorrencia/buscar")
    public PaginatedResponse<AulaOcorrencia> buscarOcorrencias(@RequestBody AulaOcorrenciaFilter filter){
        return aulaService.buscar(filter);
    }

    @PostMapping(value = "ocorrencia/cancelar/{id}")
    public void cancelar(@PathVariable Long id, @RequestBody MensagemDTO mensagem) throws RuntimeException{
        aulaService.cancelar(id, mensagem);
    }

}
