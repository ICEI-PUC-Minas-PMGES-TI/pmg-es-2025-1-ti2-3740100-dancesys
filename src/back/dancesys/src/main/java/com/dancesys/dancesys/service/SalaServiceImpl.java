package com.dancesys.dancesys.service;


import com.dancesys.dancesys.dto.SalaDTO;
import com.dancesys.dancesys.entity.Sala;
import com.dancesys.dancesys.mapper.SalaMapper;
import com.dancesys.dancesys.repository.SalaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SalaServiceImpl implements SalaService {
    private final SalaRepository salaRepository;

    public SalaServiceImpl(SalaRepository salaRepository) {
        this.salaRepository = salaRepository;
    }

    @Override
    public SalaDTO salvar(SalaDTO dto) throws Exception{
        try {
            Sala sala = salaRepository.save(SalaMapper.toEntity(dto));
            return SalaMapper.toDto(sala);
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public List<Sala> buscar(){
        return salaRepository.findAll();
    }

    @Override
    public String excluir(Long id){
        salaRepository.deleteById(id);
        return "Sala excluida com sucesso!";
    }
}
