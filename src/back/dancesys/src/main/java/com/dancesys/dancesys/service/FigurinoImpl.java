package com.dancesys.dancesys.service;

import com.dancesys.dancesys.dto.FigurinoDTO;
import com.dancesys.dancesys.entity.Figurino;
import com.dancesys.dancesys.mapper.FigurinoMapper;
import com.dancesys.dancesys.repository.FigurinoRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FigurinoImpl implements FigurinoService {

    private final FigurinoRepository figurinoRepository;

    public FigurinoImpl(FigurinoRepository figurinoRepository) {
        this.figurinoRepository = figurinoRepository;
    }

    @Override
    public FigurinoDTO salvar (FigurinoDTO dto) throws Exception {
        Figurino entity = new Figurino();
        try {
            entity = figurinoRepository.save(FigurinoMapper.toEntity(dto));
            return FigurinoMapper.toDto(entity);
        }
        catch (Exception e){
            throw new Exception("erro ao salvar figurino", e);
        }
    }

    @Override
    public List<FigurinoDTO> buscar() {
        List<FigurinoDTO> dtos = new ArrayList<>();
        List<Figurino> figurinos = figurinoRepository.findAll();
        for (Figurino entity : figurinos) {
            dtos.add(FigurinoMapper.toDto(entity));
        }
        return dtos;
    }

    @Override
    public  void deletar (Long id) {
        figurinoRepository.deleteById(id);
    }

}