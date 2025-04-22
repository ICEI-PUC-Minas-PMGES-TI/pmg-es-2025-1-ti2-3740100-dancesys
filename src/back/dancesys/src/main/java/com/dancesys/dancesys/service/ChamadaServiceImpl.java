package com.dancesys.dancesys.service;

import com.dancesys.dancesys.entity.Aluno;
import com.dancesys.dancesys.entity.Chamada;
import com.dancesys.dancesys.entity.IdsCompostos.ChamadaId;
import com.dancesys.dancesys.mapper.ChamadaMapper;
import com.dancesys.dancesys.repository.ChamadaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChamadaServiceImpl implements ChamadaService {
    private final ChamadaRepository chamadaRepository;
    private final AlunoServiceImpl alunoServiceImpl;

    public ChamadaServiceImpl(
            ChamadaRepository chamadaRepository,
            AlunoServiceImpl alunoServiceImpl
    ) {
        this.chamadaRepository = chamadaRepository;
        this.alunoServiceImpl = alunoServiceImpl;
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
                if(aluno.getCreditos() == 0){
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
        ChamadaId id =  new ChamadaId(idAluno,idAulaOcorrencia);
        chamadaRepository.deleteById(id);
        Aluno aluno = alunoServiceImpl.findById(idAluno);
        alunoServiceImpl.aumentarCredito(aluno,1);

        return "Removido com sucesso";
    }


}
