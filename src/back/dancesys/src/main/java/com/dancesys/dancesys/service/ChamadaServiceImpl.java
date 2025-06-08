package com.dancesys.dancesys.service;

import com.dancesys.dancesys.entity.Aluno;
import com.dancesys.dancesys.entity.AulaOcorrencia;
import com.dancesys.dancesys.entity.Chamada;
import com.dancesys.dancesys.entity.IdsCompostos.ChamadaId;
import com.dancesys.dancesys.mapper.ChamadaMapper;
import com.dancesys.dancesys.repository.ChamadaRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;

@Service
public class ChamadaServiceImpl implements ChamadaService {
    private final ChamadaRepository chamadaRepository;
    private final AlunoServiceImpl alunoServiceImpl;
    private final AulaOcorrenciaServiceImpl aulaOcorrenciaServiceImpl;

    public ChamadaServiceImpl(
            ChamadaRepository chamadaRepository,
            @Lazy AlunoServiceImpl alunoServiceImpl,
            @Lazy AulaOcorrenciaServiceImpl aulaOcorrenciaServiceImpl
    ) {
        this.chamadaRepository = chamadaRepository;
        this.alunoServiceImpl = alunoServiceImpl;
        this.aulaOcorrenciaServiceImpl = aulaOcorrenciaServiceImpl;
    }

    public void gerarChamada(List<Long> alunos, Long idAulaOcorrencia){
        try{
            for(Long idAluno: alunos){
                Chamada chamada = chamadaRepository.save(ChamadaMapper.toEntity(idAulaOcorrencia,idAluno,Chamada.faltante));
            }
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Chamada adicionarAluno(Long idAulaOcorrencia, Long idAluno) throws RuntimeException {
        try{
            if(chamadaRepository.findByIdAluno_Id(idAluno) !=null){
                throw new RuntimeException("você ja esta inscrito nesta aula");
            }else{
                Aluno aluno = alunoServiceImpl.findById(idAluno);
                if(aluno.getCreditos() == 0) {
                    throw new RuntimeException("Creditos insuficiente");
                }else{
                    alunoServiceImpl.dimuirCredito(aluno, 1);
                    return chamadaRepository.save(ChamadaMapper.toEntity(idAulaOcorrencia,idAluno,Chamada.faltante));
                }
            }
        }catch (RuntimeException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public String removerAluno(Long idAluno, Long idAulaOcorrencia){
        AulaOcorrencia ao = aulaOcorrenciaServiceImpl.buscarPorId(idAulaOcorrencia);

        if(LocalTime.now().isBefore(ao.getIdAula().getHorarioInicio().minusHours(1))){
            throw new RuntimeException("Você só pode se desincrever em ate 1 hora antes da aula");
        }

        Aluno aluno = alunoServiceImpl.findById(idAluno);
        ChamadaId id =  new ChamadaId(idAluno,idAulaOcorrencia);
        chamadaRepository.deleteById(id);
        alunoServiceImpl.aumentarCredito(aluno,1);

        return "Removido com sucesso";
    }

    public List<Chamada> buscarPorAula(Long idAula){
        List<Chamada> chamadas = chamadaRepository.findByIdAulaOcorrencia_Id(idAula);
        return chamadas;
    }

    public void deletarAll(List<Chamada> chamadas){
        for(Chamada chamada : chamadas){
            if(chamada.getIdAluno().getTipo().equals(Aluno.livre) && chamada.getIdAluno().getCreditos() < Aluno.max_creditos){
                Aluno aluno = alunoServiceImpl.aumentarCredito(chamada.getIdAluno(),1);
            }
        }
        chamadaRepository.deleteAll(chamadas);
    }

    public List<Chamada> findByIdAulaOcorrenciaIdAulaIdAndIdAlunoIdIn(Long idAula, List<Long> idsAlunos){
        return chamadaRepository.findByIdAulaOcorrenciaIdAulaIdAndIdAlunoIdIn(idAula,idsAlunos);
    }

}
