package com.dancesys.dancesys.service;

import com.dancesys.dancesys.dto.AulaExtraDTO;
import com.dancesys.dancesys.dto.AulaExtraFilter;
import com.dancesys.dancesys.entity.AulaExtra;
import com.dancesys.dancesys.entity.Dividendo;
import com.dancesys.dancesys.infra.PaginatedResponse;
import com.dancesys.dancesys.mapper.AulaExtraMapper;
import com.dancesys.dancesys.repository.AulaExtraRepository;
import com.dancesys.dancesys.repository.AulaExtraRepositoryCustom;
import com.dancesys.dancesys.repository.DividendoRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class AulaExtraServiceImpl implements AulaExtraService {

    private final AulaExtraRepository aulaExtraRepository;
    private final AulaExtraRepositoryCustom aulaExtraRepositoryCustom;
    private final EnsaioApresentacaoServiceImpl ensaioApresentacaoServiceImpl;
    private final AulaOcorrenciaServiceImpl aulaOcorrenciaServiceImpl;
    private final DividendoServiceImpl dividendoServiceImpl;

    public AulaExtraServiceImpl(
            AulaExtraRepository aulaExtraRepository,
            AulaExtraRepositoryCustom aulaExtraRepositoryCustom,
            EnsaioApresentacaoServiceImpl ensaioApresentacaoServiceImpl,
            AulaOcorrenciaServiceImpl aulaOcorrenciaServiceImpl,
            DividendoServiceImpl dividendoServiceImpl
            ) {
        this.aulaExtraRepository = aulaExtraRepository;
        this.aulaExtraRepositoryCustom = aulaExtraRepositoryCustom;
        this.ensaioApresentacaoServiceImpl = ensaioApresentacaoServiceImpl;
        this.aulaOcorrenciaServiceImpl = aulaOcorrenciaServiceImpl;
        this.dividendoServiceImpl = dividendoServiceImpl;
    }

    @Override
    public AulaExtraDTO salvar(AulaExtraDTO dto) throws RuntimeException {
        try {
            if(
                    !ensaioApresentacaoServiceImpl.verificaHorario(dto.getHorarioInicio(), dto.getHorarioFim(), dto.getIdProfessor()) ||
                    !aulaOcorrenciaServiceImpl.verificaHorario(dto.getHorarioInicio(), dto.getHorarioFim(), dto.getIdProfessor())
            ) {
                throw new RuntimeException("Professor com horario oculpado");
            }
            if(dto.getId() == null) {
                dto.setSituacao(AulaExtra.PENDENTE);
            }
            AulaExtra entity = aulaExtraRepository.save(AulaExtraMapper.toEntity(dto));
            return AulaExtraMapper.toDto(entity);
        } catch (RuntimeException e) {
            throw new RuntimeException(e.getMessage());
        }
    }


    @Override
    public void aceitar(Long id, Long idSala) throws RuntimeException{
        AulaExtra entity = aulaExtraRepository.findById(id).get();

        if(
                !ensaioApresentacaoServiceImpl.verificaHorario(entity.getHorarioInicio(), entity.getHorarioFim(), entity.getIdProfessor().getId()) ||
                !aulaOcorrenciaServiceImpl.verificaHorario(entity.getHorarioInicio(), entity.getHorarioFim(), entity.getIdProfessor().getId())
        ) {
            throw new RuntimeException("Professor com horario oculpado");
        }

        entity.setSituacao(AulaExtra.ACEITA);

        Duration duracao = Duration.between(entity.getHorarioInicio(), entity.getHorarioFim());
        long minutos = duracao.toMinutes();
        BigDecimal valorHora = entity.getIdProfessor().getValorHoraExtra();
        BigDecimal minutosNaHora = new BigDecimal("60");
        BigDecimal valorPorMin = valorHora.divide(minutosNaHora, 10, RoundingMode.HALF_UP);
        BigDecimal minutosBD = new BigDecimal(minutos);
        BigDecimal valor = valorPorMin.multiply(minutosBD);
        BigDecimal valorFormatado = valor.setScale(2, RoundingMode.HALF_UP);

        String codigo = String.format("%d.%d.%d.%.2f.%02d%02d%d",
                Dividendo.AULA, entity.getIdAluno().getId(), entity.getId(), valorFormatado, LocalDate.now().getDayOfMonth(), LocalDate.now().getMonthValue(), LocalDate.now().getYear());
        entity.setCodigo(codigo);

        dividendoServiceImpl.gerarAula(entity, valorFormatado);
        aulaExtraRepository.save(entity);

    }

    @Override
    public PaginatedResponse<AulaExtra> buscar(AulaExtraFilter filtro){
        return aulaExtraRepositoryCustom.buscar(filtro);
    }
}