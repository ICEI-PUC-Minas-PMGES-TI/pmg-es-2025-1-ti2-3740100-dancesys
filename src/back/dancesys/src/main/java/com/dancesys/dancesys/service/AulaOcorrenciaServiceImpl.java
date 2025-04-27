package com.dancesys.dancesys.service;

import com.dancesys.dancesys.dto.AulaDTO;
import com.dancesys.dancesys.entity.Aula;
import com.dancesys.dancesys.entity.AulaOcorrencia;
import com.dancesys.dancesys.repository.AulaOcorrenciaRepository;
import com.dancesys.dancesys.repository.ChamadaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

@Service
public class AulaOcorrenciaServiceImpl {
    private final AulaOcorrenciaRepository aulaOcorrenciaRepository;
    private final ChamadaServiceImpl chamadaServiceImpl;

    public AulaOcorrenciaServiceImpl(
            AulaOcorrenciaRepository aulaOcorrenciaRepository,
            ChamadaServiceImpl chamadaServiceImpl
    ) {
        this.aulaOcorrenciaRepository = aulaOcorrenciaRepository;
        this.chamadaServiceImpl = chamadaServiceImpl;
    }

    private void salvar(AulaOcorrencia ao, List<Long> alunos){

        try{
            if(ao.getId()==null){
                ao.setStatus(AulaOcorrencia.ATIVO);
            }

            Integer ano = ao.getData().getYear();
            Integer mes = ao.getData().getMonthValue();
            Integer dia = ao.getData().getDayOfMonth();
            Integer horainicio = ao.getIdAula().getHorarioInicio().getHour();
            Integer minutoinicio = ao.getIdAula().getHorarioInicio().getMinute();
            Integer horafim = ao.getIdAula().getHorarioInicio().getHour();
            Integer minutofim = ao.getIdAula().getHorarioInicio().getMinute();

            String codaula = String.format("%02d%02d%d.%s.%d.%02d%02d%02d%02d",
                    dia, mes, ano, ao.getIdAula().getIdModalidade().getId(), ao.getIdAula().getIdProfessor().getId(),
                    horainicio, minutoinicio, horafim, minutofim);

            ao.setCodigo(codaula);

            AulaOcorrencia newAo = aulaOcorrenciaRepository.save(ao);
            chamadaServiceImpl.gerarChamada(alunos, newAo.getId());

        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    public void gerarOcorrenciasAula(List<Long> alunos, Aula entity) throws Exception {
        LocalDate dataAtual = LocalDate.now();
        YearMonth yearMonth = YearMonth.of(dataAtual.getYear(), dataAtual.getMonth());
        YearMonth proximoMes = yearMonth.plusMonths(1);
        LocalDate ultimoDia = proximoMes.atEndOfMonth();


        while (!dataAtual.isAfter(ultimoDia)) {
            Integer dia = entity.getDiaSemana();
            if(dataAtual.getDayOfWeek().getValue() == dia.intValue()) {
                AulaOcorrencia ao = new AulaOcorrencia();
                ao.setData(dataAtual);
                ao.setIdAula(entity);
                salvar(ao, alunos);
            }
            dataAtual = dataAtual.plusDays(1);
        }
    }

    public List<AulaOcorrencia> buscarAulasPosData(LocalDate data, Long idAula){
        return aulaOcorrenciaRepository.findByIdAula_IdAndDataGreaterThan(idAula, data);
    }

    public void deletarAll(List<AulaOcorrencia> aos){
        aulaOcorrenciaRepository.deleteAll(aos);
    }

    public AulaOcorrencia buscarPorId(Long id){
        return aulaOcorrenciaRepository.findById(id).get();
    }
}
