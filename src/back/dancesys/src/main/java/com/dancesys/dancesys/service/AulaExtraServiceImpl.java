package com.dancesys.dancesys.service;

import com.dancesys.dancesys.dto.AulaExtraDTO;
import com.dancesys.dancesys.dto.AulaExtraFilter;
import com.dancesys.dancesys.entity.AulaExtra;
import com.dancesys.dancesys.infra.PaginatedResponse;
import com.dancesys.dancesys.mapper.AulaExtraMapper;
import com.dancesys.dancesys.repository.AulaExtraRepository;
import com.dancesys.dancesys.repository.AulaExtraRepositoryCustom;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AulaExtraServiceImpl implements AulaExtraService {

    private final AulaExtraRepository aulaExtraRepository;
    private final AulaExtraRepositoryCustom aulaExtraRepositoryCustom;
    private final EnsaioApresentacaoServiceImpl ensaioApresentacaoServiceImpl;
    private final AulaOcorrenciaServiceImpl aulaOcorrenciaServiceImpl;

    public AulaExtraServiceImpl(
            AulaExtraRepository aulaExtraRepository,
            AulaExtraRepositoryCustom aulaExtraRepositoryCustom,
            EnsaioApresentacaoServiceImpl ensaioApresentacaoServiceImpl,
            AulaOcorrenciaServiceImpl aulaOcorrenciaServiceImpl
    ) {
        this.aulaExtraRepository = aulaExtraRepository;
        this.aulaExtraRepositoryCustom = aulaExtraRepositoryCustom;
        this.ensaioApresentacaoServiceImpl = ensaioApresentacaoServiceImpl;
        this.aulaOcorrenciaServiceImpl = aulaOcorrenciaServiceImpl;
    }

    @Override
    public AulaExtraDTO salvar(AulaExtraDTO dto) throws RuntimeException {
        try {
            if(
                    !ensaioApresentacaoServiceImpl.verificaHorario(dto.getHorarioInicio(), dto.getHorarioFim(), dto.getIdProfessor()) ||
                    !aulaOcorrenciaServiceImpl.verificaHorario(dto.getHorarioInicio(), dto.getHorarioFim(), dto.getIdProfessor()) ||
                    verificarHorario(dto.getHorarioInicio(), dto.getHorarioFim(), dto.getIdProfessor())
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
    public PaginatedResponse<AulaExtra> buscar(AulaExtraFilter filtro){
        return aulaExtraRepositoryCustom.buscar(filtro);
    }

    public boolean verificarHorario(LocalDateTime horarioInicio, LocalDateTime horarioFim, Long idProfessor){
        List<AulaExtra> ae = aulaExtraRepository.findByHorarioInicioLessThanAndHorarioFimGreaterThanAndIdProfessorId(horarioInicio, horarioFim, idProfessor);

        return ae.isEmpty();
    }
}