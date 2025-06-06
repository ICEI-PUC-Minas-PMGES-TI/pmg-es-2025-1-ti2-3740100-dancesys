package com.dancesys.dancesys.service;

import com.dancesys.dancesys.dto.AulaExtraDTO;
import com.dancesys.dancesys.dto.AulaExtraFilter;
import com.dancesys.dancesys.entity.AulaExtra;
import com.dancesys.dancesys.infra.PaginatedResponse;
import com.dancesys.dancesys.mapper.AulaExtraMapper;
import com.dancesys.dancesys.repository.AulaExtraRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AulaExtraServiceImpl implements AulaExtraService {

    private final AulaExtraRepository aulaExtraRepository;

    public AulaExtraServiceImpl(AulaExtraRepository aulaExtraRepository) {
        this.aulaExtraRepository = aulaExtraRepository;
    }

    @Override
    public AulaExtraDTO salvar(AulaExtraDTO dto) throws Exception {
        try {
            AulaExtra entity = aulaExtraRepository.save(AulaExtraMapper.toEntity(dto));
            return AulaExtraMapper.toDto(entity);
        } catch (Exception e) {
            throw new Exception("Erro ao salvar aula extra", e);
        }
    }

    @Override
    public PaginatedResponse<AulaExtra> buscar(AulaExtraFilter filtro) {
        List<AulaExtra> result = aulaExtraRepository.findByCodigoAndSituacaoAndIdProfessor(
                filtro.getCodigo(),
                filtro.getSituacao(),
                filtro.getIdProfessor()
        );

        PaginatedResponse<AulaExtra> response = new PaginatedResponse<>(result, result.size());

        return response;
    }


    @Override
    public void deletar(Long id) {
        aulaExtraRepository.deleteById(id);
    }
}