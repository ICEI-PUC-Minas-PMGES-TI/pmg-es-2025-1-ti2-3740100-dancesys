package com.dancesys.dancesys.service;

import com.dancesys.dancesys.dto.AulaDto;
import com.dancesys.dancesys.mapper.AulaMapper;
import com.dancesys.dancesys.repository.AulaRepository;
import com.dancesys.dancesys.entity.Aula;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AulaServiceImpl implements AulaService{

    private final AulaRepository aulaRepository;

    public AulaServiceImpl(AulaRepository aulaRepository) {
        this.aulaRepository = aulaRepository;
    }

    @Override
    public AulaDto salvar(AulaDto dto) throws Exception{
        Aula aula = new Aula();

        try{
            aula = aulaRepository.save(AulaMapper.toEntity(dto));
            AulaDto newDTO = AulaMapper.toDto(aula);
            return newDTO;
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public ResponseEntity<Aula> excluir(Long id) throws Exception{
        try {
            aulaRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }catch(Exception e){
            throw new Exception(e);
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
