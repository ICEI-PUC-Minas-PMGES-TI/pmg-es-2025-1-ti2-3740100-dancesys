package com.dancesys.dancesys.service;

import com.dancesys.dancesys.dto.AulaDto;
import com.dancesys.dancesys.dto.AulaOcorrenciaDto;
import com.dancesys.dancesys.dto.ChamadaAulaDto;
import com.dancesys.dancesys.entity.Aula;
import com.dancesys.dancesys.entity.AulaOcorrencia;
import com.dancesys.dancesys.entity.ChamadaAula;
import com.dancesys.dancesys.entity.Usuario;
import com.dancesys.dancesys.mapper.AulaMapper;
import com.dancesys.dancesys.mapper.AulaOcorrenciaMapper;
import com.dancesys.dancesys.mapper.ChamadaAulaMapper;
import com.dancesys.dancesys.repository.AulaOcorrenciaRepository;
import com.dancesys.dancesys.repository.UsuarioRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;

@Service
public class AulaOcorrenciaServiceImpl implements AulaOcorrenciaService {
    private AulaOcorrenciaRepository aulaOcorrenciaRepository;
    private AulaServiceImpl aulaServiceImpl;
    private UsuarioRepository usuarioRepository;
    private ChamadaAulaServiceImpl chamadaAulaServiceImpl;

    public AulaOcorrenciaServiceImpl(
            AulaOcorrenciaRepository aulaOcorrenciaRepository,
            @Lazy AulaServiceImpl aulaServiceImpl,
            UsuarioRepository usuarioRepository,
            ChamadaAulaServiceImpl chamadaAulaServiceImpl

    ) {
        this.aulaOcorrenciaRepository = aulaOcorrenciaRepository;
        this.aulaServiceImpl = aulaServiceImpl;
        this.usuarioRepository = usuarioRepository;
        this.chamadaAulaServiceImpl = chamadaAulaServiceImpl;
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
            if(dataAtual.getDayOfWeek().getValue() == dia.intValue()) {
                AulaOcorrencia ao = new AulaOcorrencia();
                ao.setData(dataAtual);
                ao.setListaIdsAlunos(dto.getListaIdsAlunos());
                ao.setIdAula(AulaMapper.toEntity(dto));
                salvar(AulaOcorrenciaMapper.toDto(ao));
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
        Set<String> novosIdsAlunosSet = new HashSet<>(Arrays.asList(novaListaIdsAlunos.split("/")));
        LocalDate dataAtual = LocalDate.now();
        LocalDate dataCerta = dataAtual.minusDays(1);
        List<AulaOcorrencia> aulasOcorrentes = aulaOcorrenciaRepository.findByIdAula_IdAndDataGreaterThan(dto.getId(), dataCerta);

        for (AulaOcorrencia aulaOcorrencia : aulasOcorrentes) {
            Set<String> listaIdsAlunosExistenteSet = new HashSet<>(Arrays.asList(aulaOcorrencia.getListaIdsAlunos().split("/")));
            listaIdsAlunosExistenteSet.addAll(novosIdsAlunosSet);
            aulaOcorrencia.setListaIdsAlunos(String.join("/", listaIdsAlunosExistenteSet));

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

    @Override
    public List<ChamadaAulaDto> gerarChamadaAula(Long id) throws Exception{
        AulaOcorrencia entity = aulaOcorrenciaRepository.findById(id).get();
        List<ChamadaAulaDto> alunos = new ArrayList<>();
        String[] idsAlunos = entity.getListaIdsAlunos().split("/");
        for(String idAluno : idsAlunos) {
            if (idAluno != null && !idAluno.trim().isEmpty()) {
                Long idA = Long.parseLong(idAluno.trim());
                Usuario aluno = new Usuario();
                aluno = usuarioRepository.findById(idA).get();

                ChamadaAula chamadaAula = new ChamadaAula();
                chamadaAula.setIdAulaOcorrencia(entity);
                chamadaAula.setIdAluno(aluno);

                ChamadaAulaDto dto = chamadaAulaServiceImpl.salvar(ChamadaAulaMapper.toDto(chamadaAula));
                alunos.add(dto);
            }
        }
        return alunos;
    }
}