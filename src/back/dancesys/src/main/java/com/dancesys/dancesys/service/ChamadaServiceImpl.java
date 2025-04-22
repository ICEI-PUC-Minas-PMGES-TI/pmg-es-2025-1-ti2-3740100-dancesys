package com.dancesys.dancesys.service;

import com.dancesys.dancesys.entity.Chamada;
import com.dancesys.dancesys.entity.IdsCompostos.ChamadaId;
import com.dancesys.dancesys.mapper.ChamadaMapper;
import com.dancesys.dancesys.repository.ChamadaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChamadaServiceImpl {
    private final ChamadaRepository chamadaRepository;

    public ChamadaServiceImpl(ChamadaRepository chamadaRepository) {
        this.chamadaRepository = chamadaRepository;
    }

    public void gerarChamada(List<Long> alunos, Long idAulaOcorrencia){
        try{
            for(Long idAluno: alunos){
                ChamadaId id = new ChamadaId(idAluno,idAulaOcorrencia);
                Chamada chamada = chamadaRepository.save(ChamadaMapper.toEntity(id,idAulaOcorrencia,idAluno,Chamada.faltante));
            }
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }
}
