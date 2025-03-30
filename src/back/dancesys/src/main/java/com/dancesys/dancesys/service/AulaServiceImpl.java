package com.dancesys.dancesys.service;

import com.dancesys.dancesys.dto.AulaDto;
import com.dancesys.dancesys.entity.AulaOcorrencia;
import com.dancesys.dancesys.mapper.AulaMapper;
import com.dancesys.dancesys.repository.AulaOcorrenciaRepository;
import com.dancesys.dancesys.repository.AulaRepository;
import com.dancesys.dancesys.entity.Aula;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class AulaServiceImpl implements AulaService{

    private final AulaRepository aulaRepository;
    private final AulaOcorrenciaRepository aulaOcorrenciaRepository;
    private final AulaOcorrenciaServiceImpl aulaOcorrenciaServiceImpl;

    public AulaServiceImpl(
            AulaRepository aulaRepository,
            AulaOcorrenciaRepository aulaOcorrenciaRepository,
            @Lazy AulaOcorrenciaServiceImpl aulaOcorrenciaServiceImpl
    ) {
        this.aulaRepository = aulaRepository;
        this.aulaOcorrenciaRepository = aulaOcorrenciaRepository;
        this.aulaOcorrenciaServiceImpl = aulaOcorrenciaServiceImpl;
    }

    @Override
    public AulaDto salvar(AulaDto dto) throws Exception{
        Aula aula = new Aula();

        try{
            if(dto.getId() == null){
                dto.setStatus(Aula.ativo);
            }
            aula = aulaRepository.save(AulaMapper.toEntity(dto));
            AulaDto newDTO = AulaMapper.toDto(aula);
            if(dto.getId() == null){
                aulaOcorrenciaServiceImpl.gerarOcorrenciasAula(newDTO);
            }
            return newDTO;
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public ResponseEntity<Aula> desativar(Long id) throws Exception{
        try {
            Aula a = aulaRepository.findById(id).get();
            AulaDto dto = AulaMapper.toDto(a);
            dto.setStatus(AulaOcorrencia.inativo);
            salvar(dto);
            List<AulaOcorrencia> aulasocorrentes = aulaOcorrenciaRepository.findByIdAula_IdAndDataGreaterThan(id, LocalDate.now());
            for(AulaOcorrencia ao : aulasocorrentes){
                aulaOcorrenciaServiceImpl.excluir(ao.getId());
            }
            return ResponseEntity.ok().build();
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Aula> findAllAulas(){
        return aulaRepository.findAll();
    }

    public Aula findById(Long id){
        return aulaRepository.findById(id).get();
    }
}
