package com.dancesys.dancesys.service;

import com.dancesys.dancesys.dto.AulaDTO;
import com.dancesys.dancesys.entity.Aula;
import com.dancesys.dancesys.entity.AulaOcorrencia;
import com.dancesys.dancesys.repository.AulaOcorrenciaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;

@Service
public class AulaOcorrenciaServiceImpl {
    private final AulaOcorrenciaRepository aulaOcorrenciaRepository;

    public AulaOcorrenciaServiceImpl(AulaOcorrenciaRepository aulaOcorrenciaRepository) {
        this.aulaOcorrenciaRepository = aulaOcorrenciaRepository;
    }

    private void salvar(AulaOcorrencia ao){

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

            aulaOcorrenciaRepository.save(ao);
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    public void gerarOcorrenciasAula(AulaDTO dto, Aula entity) throws Exception {
        LocalDate dataAtual = LocalDate.now();
        YearMonth yearMonth = YearMonth.of(dataAtual.getYear(), dataAtual.getMonth());
        YearMonth proximoMes = yearMonth.plusMonths(1);
        LocalDate ultimoDia = proximoMes.atEndOfMonth();


        while (!dataAtual.isAfter(ultimoDia)) {
            Integer dia = dto.getDiaSemana();
            if(dataAtual.getDayOfWeek().getValue() == dia.intValue()) {
                AulaOcorrencia ao = new AulaOcorrencia();
                ao.setData(dataAtual);
                ao.setIdAula(entity);
                salvar(ao);
            }
            dataAtual = dataAtual.plusDays(1);
        }
    }
}
