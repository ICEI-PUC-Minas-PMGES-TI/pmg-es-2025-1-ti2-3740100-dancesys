package com.dancesys.dancesys.service;

import com.dancesys.dancesys.dto.AulaExperimentalDTO;
import com.dancesys.dancesys.dto.AulaExperimentalFilter;
import com.dancesys.dancesys.entity.AulaExperimental;
import com.dancesys.dancesys.infra.PaginatedResponse;
import com.dancesys.dancesys.mapper.AulaExperimentalMapper;
import com.dancesys.dancesys.repository.AulaExperimentalRepository;
import com.dancesys.dancesys.repository.AulaExperimentalRepositoryCustom;
import org.springframework.stereotype.Service;

@Service
public class AulaExperimentalServiceImpl implements  AulaExperimentalService {
    private final AulaExperimentalRepository aulaexperimentalRepository;
    private final AulaExperimentalRepositoryCustom aulaexperimentalRepositoryCustom;

    public AulaExperimentalServiceImpl(
            AulaExperimentalRepository aulaexperimentalRepository,
            AulaExperimentalRepositoryCustom aulaexperimentalRepositoryCustom
    ) {
        this.aulaexperimentalRepository = aulaexperimentalRepository;
        this.aulaexperimentalRepositoryCustom = aulaexperimentalRepositoryCustom;
    }

    @Override
    public AulaExperimentalDTO salvar(AulaExperimentalDTO dto) throws RuntimeException {
        AulaExperimental entity = new AulaExperimental();
        try {
            if(dto.getId() == null){
                dto.setSituacao(AulaExperimental.PENDENTE);
            }
            entity = aulaexperimentalRepository.save(AulaExperimentalMapper.toEntity(dto));

            return AulaExperimentalMapper.toDto(entity);
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public PaginatedResponse<AulaExperimental> buscar(AulaExperimentalFilter filtro){
        return  aulaexperimentalRepositoryCustom.buscar(filtro);
    }
}
