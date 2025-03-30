package com.dancesys.dancesys.service;

import com.dancesys.dancesys.dto.AulaDto;
import com.dancesys.dancesys.dto.AulaOcorrenciaDto;
import com.dancesys.dancesys.entity.Aula;
import com.dancesys.dancesys.entity.AulaOcorrencia;
import com.dancesys.dancesys.mapper.AulaMapper;
import com.dancesys.dancesys.mapper.AulaOcorrenciaMapper;
import com.dancesys.dancesys.repository.AulaOcorrenciaRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class AulaOcorrenciaServiceImpl implements AulaOcorrenciaService {
    private AulaOcorrenciaRepository aulaOcorrenciaRepository;
    private AulaServiceImpl aulaServiceImpl;

    public AulaOcorrenciaServiceImpl(
            AulaOcorrenciaRepository aulaOcorrenciaRepository,
            @Lazy AulaServiceImpl aulaServiceImpl

    ) {
        this.aulaOcorrenciaRepository = aulaOcorrenciaRepository;
        this.aulaServiceImpl = aulaServiceImpl;
    }

    @Override
    public AulaOcorrenciaDto salvar(AulaOcorrenciaDto dto) throws Exception{
        AulaOcorrencia aula = new AulaOcorrencia();

        try{
            if(dto.getId() == null){
                dto.setStatus(AulaOcorrencia.ativo);
            }

            int dia = dto.getData().getDayOfMonth();
            int mes = dto.getData().getMonthValue();
            int ano = dto.getData().getYear();

            Aula a = aulaServiceImpl.findById(dto.getIdAula().getId());
            int horainicio = a.getHorarioInicio().getHour();
            int minutoinicio = a.getHorarioInicio().getMinute();
            int horafim = a.getHorarioFim().getHour();
            int minutofim = a.getHorarioFim().getMinute();

            String codaula = String.format("%02d%02d%d.%s.%d.%02d%02d%02d%02d",
                    dia, mes, ano, a.getTipo(), a.getIdProfessor().getId(),
                    horainicio, minutoinicio, horafim, minutofim);


            dto.setCodigoAula(codaula);

            aula = aulaOcorrenciaRepository.save(AulaOcorrenciaMapper.toEntity(dto));
            AulaOcorrenciaDto newDTO = AulaOcorrenciaMapper.toDto(aula);
            return newDTO;
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    public void gerarOcorrenciasAula(AulaDto dto) throws Exception {
        LocalDate dataAtual = LocalDate.now();
        YearMonth yearMonth = YearMonth.of(dataAtual.getYear(), dataAtual.getMonth());
        YearMonth proximoMes = yearMonth.plusMonths(1);
        LocalDate ultimoDia = proximoMes.atEndOfMonth();


        while (!dataAtual.isAfter(ultimoDia)) {
            Integer dia = dto.getDiaSemana();
            int teste = dataAtual.getDayOfWeek().getValue();
            if(dataAtual.getDayOfWeek().getValue() == dia.intValue()) {
                AulaOcorrencia ao = new AulaOcorrencia();
                ao.setData(dataAtual);
                ao.setListaIdsAlunos(dto.getListaIdsAlunos());
                ao.setIdAula(AulaMapper.toEntity(dto));
                aulaOcorrenciaRepository.save(ao);
            }
            dataAtual = dataAtual.plusDays(1);
        }
    }

    public void atualizarOcorrenciasAula(AulaDto dto) throws Exception {
        LocalDate dataAtual = LocalDate.now();
        LocalDate dataCerta = dataAtual.minusDays(1);
        List<AulaOcorrencia> aulasOcorrentes = aulaOcorrenciaRepository.findByIdAula_IdAndDataGreaterThan(dto.getId(), dataCerta);

        Integer novoDiaSemana = dto.getDiaSemana();

        for (AulaOcorrencia aulaOcorrencia : aulasOcorrentes) {
            int diasParaAtualizar = (novoDiaSemana - aulaOcorrencia.getData().getDayOfWeek().getValue() + 7) % 7;
            LocalDate novaData = aulaOcorrencia.getData().plusDays(diasParaAtualizar);
            aulaOcorrencia.setData(novaData);

            aulaOcorrenciaRepository.save(aulaOcorrencia);
        }
    }

    public void atualizarListaIdsAlunos(AulaDto dto) throws Exception {
        String novaListaIdsAlunos = dto.getListaIdsAlunos();
        Set<String> novosIdsAlunosSet = new HashSet<>(Arrays.asList(novaListaIdsAlunos.split(",")));
        LocalDate dataAtual = LocalDate.now();
        LocalDate dataCerta = dataAtual.minusDays(1);
        List<AulaOcorrencia> aulasOcorrentes = aulaOcorrenciaRepository.findByIdAula_IdAndDataGreaterThan(dto.getId(), dataCerta);

        for (AulaOcorrencia aulaOcorrencia : aulasOcorrentes) {
            Set<String> listaIdsAlunosExistenteSet = new HashSet<>(Arrays.asList(aulaOcorrencia.getListaIdsAlunos().split(",")));
            listaIdsAlunosExistenteSet.addAll(novosIdsAlunosSet);
            aulaOcorrencia.setListaIdsAlunos(String.join(",", listaIdsAlunosExistenteSet));

            aulaOcorrenciaRepository.save(aulaOcorrencia);
        }
    }



    @Override
    public AulaOcorrenciaDto cancelar(AulaOcorrenciaDto dto) throws Exception{
        try{
            dto.setStatus(AulaOcorrencia.inativo);
            return salvar(dto);
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    public ResponseEntity<AulaOcorrencia> excluir(Long id) throws Exception{
        try{
            aulaOcorrenciaRepository.deleteById(id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }
}