package com.dancesys.dancesys.service;

import com.dancesys.dancesys.dto.AulaDTO;
import com.dancesys.dancesys.entity.Aula;
import com.dancesys.dancesys.entity.AulaAluno;
import com.dancesys.dancesys.entity.AulaOcorrencia;
import com.dancesys.dancesys.entity.Chamada;
import com.dancesys.dancesys.mapper.AulaMapper;
import com.dancesys.dancesys.repository.AulaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class AulaServiceImpl implements  AulaService {

    private final AulaRepository aulaRepository;
    private final AulaAlunoServiceImpl aulaAlunoServiceImpl;
    private final AulaOcorrenciaServiceImpl aulaOcorrenciaServiceImpl;
    private final ChamadaServiceImpl chamadaServiceImpl;

    public AulaServiceImpl(
            AulaRepository aulaRepository,
            AulaAlunoServiceImpl aulaAlunoServiceImpl,
            AulaOcorrenciaServiceImpl aulaOcorrenciaServiceImpl,
            ChamadaServiceImpl chamadaServiceImpl
    ) {
        this.aulaRepository = aulaRepository;
        this.aulaAlunoServiceImpl = aulaAlunoServiceImpl;
        this.aulaOcorrenciaServiceImpl = aulaOcorrenciaServiceImpl;
        this.chamadaServiceImpl = chamadaServiceImpl;
    }

    @Override
    public AulaDTO salvar(AulaDTO dto) throws Exception{
        try{
            if(dto.getId()==null) dto.setStatus(Aula.ativo);

            Aula entity = aulaRepository.save(AulaMapper.toEntity(dto));
            aulaAlunoServiceImpl.salvar(dto.getAlunos(),entity.getId());

            if(dto.getId()==null){
                aulaOcorrenciaServiceImpl.gerarOcorrenciasAula(dto.getAlunos(), entity);
            }
            return AulaMapper.toDto(entity);
        }catch(Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public String mudarStatus(Long id) throws Exception{
        Aula aula = aulaRepository.findById(id).get();
        if(aula.getStatus().equals(Aula.ativo)){
            aula.setStatus(Aula.desativo);
            List<AulaOcorrencia> aulas = aulaOcorrenciaServiceImpl.buscarAulasPosData(LocalDate.now(), id);
            for(AulaOcorrencia ao : aulas){
               List<Chamada> chamada = chamadaServiceImpl.buscarPorAula(ao.getId());
               chamadaServiceImpl.deletarAll(chamada);
            }
            aulaOcorrenciaServiceImpl.deletarAll(aulas);
        }else{
            aula.setStatus(Aula.ativo);
            List<AulaAluno> aulas = aulaAlunoServiceImpl.buscarPorAula(id);
            List<Long> idsAlunos = new ArrayList<>();
            for(AulaAluno ao : aulas){
                idsAlunos.add(ao.getIdAluno().getId());
            }
            aulaOcorrenciaServiceImpl.gerarOcorrenciasAula(idsAlunos, aula);
        }
        aulaRepository.save(aula);
        return "Status alterado com sucesso";
    }
}
