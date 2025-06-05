package com.dancesys.dancesys.service;

import com.dancesys.dancesys.dto.FigurinoDTO;
import com.dancesys.dancesys.dto.FigurinoFilter;
import com.dancesys.dancesys.entity.Figurino;
import com.dancesys.dancesys.infra.PaginatedResponse;
import com.dancesys.dancesys.mapper.FigurinoMapper;
import com.dancesys.dancesys.repository.FigurinoRepository;
import com.dancesys.dancesys.repository.FigurinoRepositoryCustom;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FigurinoServiceImpl implements FigurinoService {

    private final FigurinoRepository figurinoRepository;
    private final FigurinoRepositoryCustom figurinoRepositoryCustom;

    public FigurinoServiceImpl(
            FigurinoRepository figurinoRepository,
            FigurinoRepositoryCustom figurinoRepositoryCustom
    ) {
        this.figurinoRepository = figurinoRepository;
        this.figurinoRepositoryCustom = figurinoRepositoryCustom;
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
    public PaginatedResponse<Figurino> buscar(FigurinoFilter filtro) {
        return figurinoRepositoryCustom.buscar(filtro);
    }

    @Override
    public  void deletar (Long id) {
        figurinoRepository.deleteById(id);
    }

}