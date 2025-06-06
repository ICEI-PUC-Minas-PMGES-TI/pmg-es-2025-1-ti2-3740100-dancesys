package com.dancesys.dancesys.service;

import com.dancesys.dancesys.dto.AulaOcorrenciaFilter;
import com.dancesys.dancesys.dto.MensagemDTO;
import com.dancesys.dancesys.entity.Aula;
import com.dancesys.dancesys.entity.AulaOcorrencia;
import com.dancesys.dancesys.entity.Chamada;
import com.dancesys.dancesys.infra.PaginatedResponse;
import com.dancesys.dancesys.repository.AulaOcorrenciaRepository;
import com.dancesys.dancesys.repository.AulaOcorrenciaRepositoryCustom;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;
import java.util.List;

@Service
public class AulaOcorrenciaServiceImpl {
    private final AulaOcorrenciaRepository aulaOcorrenciaRepository;
    private final ChamadaServiceImpl chamadaServiceImpl;
    private final AulaOcorrenciaRepositoryCustom aulaOcorrenciaRepositoryCustom;

    public AulaOcorrenciaServiceImpl(
            AulaOcorrenciaRepository aulaOcorrenciaRepository,
            @Lazy ChamadaServiceImpl chamadaServiceImpl,
            AulaOcorrenciaRepositoryCustom aulaOcorrenciaRepositoryCustom) {
        this.aulaOcorrenciaRepository = aulaOcorrenciaRepository;
        this.chamadaServiceImpl = chamadaServiceImpl;
        this.aulaOcorrenciaRepositoryCustom = aulaOcorrenciaRepositoryCustom;
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


    public void gerarOcrrenciaAulaJob(List<Long> alunos, Aula entity) throws Exception {
        LocalDate dataAtual = LocalDate.now();
        YearMonth yearMonth = YearMonth.of(dataAtual.getYear(), dataAtual.getMonth());
        YearMonth proximoMes = yearMonth.plusMonths(1);
        LocalDate ultimoDia = proximoMes.atEndOfMonth();


        while (!dataAtual.plusMonths(1).isAfter(ultimoDia)) {
            Integer dia = entity.getDiaSemana();
            if(dataAtual.getDayOfWeek().getValue() == dia.intValue()) {
                AulaOcorrencia ao = new AulaOcorrencia();
                ao.setData(dataAtual);
                ao.setIdAula(entity);
                salvar(ao, alunos);
            }
            dataAtual = dataAtual.plusDays(1);
        }

        System.out.println("\n\n\n\n\n\n\nFinalizando job mensal de aulas\n\n\n\n\n\n\n");
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

    public PaginatedResponse<AulaOcorrencia> buscar(AulaOcorrenciaFilter filtro){
        return aulaOcorrenciaRepositoryCustom.buscar(filtro);
    }

    public void cancelar(Long id, MensagemDTO mensagem) throws RuntimeException{
        AulaOcorrencia ao = buscarPorId(id);
        if(ao.getStatus().equals(AulaOcorrencia.INATIVO)){
            throw new RuntimeException("Aula ja cancelada");
        }
        ao.setMotivoCancelamento(mensagem.getMensagem());
        ao.setStatus(AulaOcorrencia.INATIVO);
        List<Chamada> chamada = chamadaServiceImpl.buscarPorAula(id);
        if(!chamada.isEmpty()){
            chamadaServiceImpl.deletarAll(chamada);
        }
        aulaOcorrenciaRepository.save(ao);
    }


    public boolean verificaHorario(LocalDateTime inicio, LocalDateTime fim, Long idProfessor) {
        LocalDate data = inicio.toLocalDate();
        LocalTime novoInicio = inicio.toLocalTime();
        LocalTime novoFim = fim.toLocalTime();

        List<AulaOcorrencia> ocorrencias = aulaOcorrenciaRepository.findByDataAndIdAula_IdProfessor_IdAndStatus(data, idProfessor, AulaOcorrencia.ATIVO);

        return ocorrencias.stream().noneMatch(ocorrencia -> {
            LocalTime aulaInicio = ocorrencia.getIdAula().getHorarioInicio();
            LocalTime aulaFim = ocorrencia.getIdAula().getHorarioFim();
            return novoInicio.isBefore(aulaFim) && novoFim.isAfter(aulaInicio);
        });
    }
}
