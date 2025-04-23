package com.dancesys.dancesys.service;

import com.dancesys.dancesys.dto.AulaDTO;
import com.dancesys.dancesys.entity.Aula;
import com.dancesys.dancesys.mapper.AulaMapper;
import com.dancesys.dancesys.repository.AulaRepository;
import org.springframework.stereotype.Service;

@Service
public class AulaServiceImpl implements  AulaService {

    private final AulaRepository aulaRepository;
    private final AulaAlunoServiceImpl aulaAlunoServiceImpl;
    private final AulaOcorrenciaServiceImpl aulaOcorrenciaServiceImpl;

    public AulaServiceImpl(
            AulaRepository aulaRepository,
            AulaAlunoServiceImpl aulaAlunoServiceImpl,
            AulaOcorrenciaServiceImpl aulaOcorrenciaServiceImpl
    ) {
        this.aulaRepository = aulaRepository;
        this.aulaAlunoServiceImpl = aulaAlunoServiceImpl;
        this.aulaOcorrenciaServiceImpl = aulaOcorrenciaServiceImpl;
    }

    @Override
    public AulaDTO salvar(AulaDTO dto) throws Exception{
        try{
            if(dto.getId()==null) dto.setStatus(Aula.ativo);

            Aula entity = aulaRepository.save(AulaMapper.toEntity(dto));
            aulaAlunoServiceImpl.salvar(dto.getAlunos(),entity.getId());

            if(dto.getId()==null){
                aulaOcorrenciaServiceImpl.gerarOcorrenciasAula(dto, entity);
            }
            return AulaMapper.toDto(entity);
        }catch(Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

}
