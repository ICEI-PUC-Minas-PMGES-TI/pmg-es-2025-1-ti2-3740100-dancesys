package com.dancesys.dancesys.service;

import com.dancesys.dancesys.dto.FigurinoApresentacaoDTO;
import com.dancesys.dancesys.entity.FigurinoApresentacao;
import com.dancesys.dancesys.mapper.FigurinoApresentacaoMapper;
import com.dancesys.dancesys.repository.FigurinoApresentacaoRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FigurinoApresentacaoServiceImpl implements FigurinoApresentacaoService {

    private final FigurinoApresentacaoRepository figurinoApresentacaoRepository;

    public FigurinoApresentacaoServiceImpl(FigurinoApresentacaoRepository figurinoApresentacaoRepository) {
        this.figurinoApresentacaoRepository = figurinoApresentacaoRepository;
    }

    @Override
    public FigurinoApresentacaoDTO salvar (FigurinoApresentacaoDTO dto) throws Exception {
        FigurinoApresentacao entity = new FigurinoApresentacao();
        try {
            entity = figurinoApresentacaoRepository.save(FigurinoApresentacaoMapper.toEntity(dto));
            return FigurinoApresentacaoMapper.toDto(entity);
        }
        catch (Exception e){
            throw new Exception("erro ao salvar figurino", e);
        }
    }

    @Override
    public List<FigurinoApresentacaoDTO> buscar() {
        List<FigurinoApresentacaoDTO> dtos = new ArrayList<>();
        List<FigurinoApresentacao> figurinosApresentacao = figurinoApresentacaoRepository.findAll();
        for (FigurinoApresentacao entity : figurinosApresentacao) {
            dtos.add(FigurinoApresentacaoMapper.toDto(entity));
        }
        return dtos;
    }

    @Override
    public  void deletar (Long id) {
        figurinoApresentacaoRepository.deleteById(id);
    }

}