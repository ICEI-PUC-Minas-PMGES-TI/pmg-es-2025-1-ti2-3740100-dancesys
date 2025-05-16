package com.dancesys.dancesys.service;

import com.dancesys.dancesys.dto.AulaDTO;
import com.dancesys.dancesys.dto.AulaFilter;
import com.dancesys.dancesys.dto.AulaOcorrenciaFilter;
import com.dancesys.dancesys.dto.MensagemDTO;
import com.dancesys.dancesys.entity.Aula;
import com.dancesys.dancesys.entity.AulaAluno;
import com.dancesys.dancesys.entity.AulaOcorrencia;
import com.dancesys.dancesys.entity.Chamada;
import com.dancesys.dancesys.infra.PaginatedResponse;
import com.dancesys.dancesys.mapper.AulaMapper;
import com.dancesys.dancesys.repository.AulaRepository;
import com.dancesys.dancesys.repository.AulaRepositoryCustom;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AulaServiceImpl implements  AulaService {

    private final AulaRepository aulaRepository;
    private final AulaAlunoServiceImpl aulaAlunoServiceImpl;
    private final AulaOcorrenciaServiceImpl aulaOcorrenciaServiceImpl;
    private final ChamadaServiceImpl chamadaServiceImpl;
    private final AulaRepositoryCustom aulaRepositoryCustom;

    public AulaServiceImpl(
            AulaRepository aulaRepository,
            AulaAlunoServiceImpl aulaAlunoServiceImpl,
            AulaOcorrenciaServiceImpl aulaOcorrenciaServiceImpl,
            ChamadaServiceImpl chamadaServiceImpl,
            AulaRepositoryCustom aulaRepositoryCustom
    ){
        this.aulaRepository = aulaRepository;
        this.aulaAlunoServiceImpl = aulaAlunoServiceImpl;
        this.aulaOcorrenciaServiceImpl = aulaOcorrenciaServiceImpl;
        this.chamadaServiceImpl = chamadaServiceImpl;
        this.aulaRepositoryCustom = aulaRepositoryCustom;
    }

    @Override
    public AulaDTO salvar(AulaDTO dto) throws Exception{
        try{
            if(dto.getId()==null){
                dto.setStatus(Aula.ativo);
            }else{
                List<AulaAluno> alunosNotIn = aulaAlunoServiceImpl.alunosNotIn(dto.getId(), dto.getAlunos());
                if(!alunosNotIn.isEmpty()){
                    List<Long> idsNotIn = alunosNotIn.stream()
                            .map(aulaAluno -> aulaAluno.getIdAluno().getId())
                            .collect(Collectors.toList());

                    chamadaServiceImpl.deletarAll(chamadaServiceImpl.findByIdAulaOcorrenciaIdAulaIdAndIdAlunoIdIn(dto.getId(), idsNotIn));
                }
            }

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

    @Override
   public void gerarAulasJobMensal() throws Exception{
       List<Aula> aulas = aulaRepository.findByStatus(Aula.ativo);
       for(Aula  a : aulas){
           List<Long> idsAlunos = aulaAlunoServiceImpl.buscarPorAula(a.getId())
                   .stream()
                   .map(aulaAluno -> aulaAluno.getIdAluno().getId())
                   .collect(Collectors.toList());
           aulaOcorrenciaServiceImpl.gerarOcrrenciaAulaJob(idsAlunos, a);
       }
    }

    @Override
    public PaginatedResponse<Aula> buscar(AulaFilter filter){
        return aulaRepositoryCustom.buscarAulas(filter);
    }

    @Override
    public PaginatedResponse<AulaOcorrencia> buscar(AulaOcorrenciaFilter filtro){
        return aulaOcorrenciaServiceImpl.buscar(filtro);
    }

    @Override
    public void cancelar(Long id, MensagemDTO mensagem) throws RuntimeException{
        aulaOcorrenciaServiceImpl.cancelar(id, mensagem);
    }
}
