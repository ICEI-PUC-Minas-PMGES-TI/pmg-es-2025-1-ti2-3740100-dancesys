package com.dancesys.dancesys.service;

import com.dancesys.dancesys.dto.AulaExperimentalDTO;
import com.dancesys.dancesys.dto.AulaExperimentalFilter;
import com.dancesys.dancesys.dto.MensagemDTO;
import com.dancesys.dancesys.entity.AulaExperimental;
import com.dancesys.dancesys.infra.PaginatedResponse;
import com.dancesys.dancesys.mapper.AulaExperimentalMapper;
import com.dancesys.dancesys.repository.AulaExperimentalRepository;
import com.dancesys.dancesys.repository.AulaExperimentalRepositoryCustom;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

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
                dto.setCriadoEm(LocalDate.now());
            }
            entity = aulaexperimentalRepository.save(AulaExperimentalMapper.toEntity(dto));

            return AulaExperimentalMapper.toDto(entity);
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void converter(Long id) throws RuntimeException{
        AulaExperimental entity = aulaexperimentalRepository.findById(id).get();
        if(!entity.getSituacao().equals(AulaExperimental.PENDENTE)){
            throw new RuntimeException("Situação já alterada");
        }
        entity.setSituacao(AulaExperimental.CONVERTIDO);
        entity.setFinalizadoEm(LocalDate.now());

        aulaexperimentalRepository.save(entity);
    }

    @Override
    public void rejeitar(Integer motivo, Long id, MensagemDTO msg) throws RuntimeException{
        AulaExperimental entity = aulaexperimentalRepository.findById(id).get();
        if(!entity.getSituacao().equals(AulaExperimental.PENDENTE)){
            throw new RuntimeException("Situação já alterada");
        }

        entity.setSituacao(AulaExperimental.RECUSADO);
        entity.setFinalizadoEm(LocalDate.now());
        entity.setMotivo(motivo);
        entity.setMotivoOutro(msg.getMensagem());

        aulaexperimentalRepository.save(entity);
    }

    @Override
    public PaginatedResponse<AulaExperimental> buscar(AulaExperimentalFilter filtro){
        return  aulaexperimentalRepositoryCustom.buscar(filtro);
    }
}
