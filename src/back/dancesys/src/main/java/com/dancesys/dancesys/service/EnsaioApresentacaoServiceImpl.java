package com.dancesys.dancesys.service;

import com.dancesys.dancesys.dto.EnsaioApresentacaoDTO;
import com.dancesys.dancesys.entity.EnsaioApresentacao;
import com.dancesys.dancesys.mapper.EnsaioApresentacaoMapper;
import com.dancesys.dancesys.repository.EnsaioApresentacaoRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EnsaioApresentacaoServiceImpl implements EnsaioApresentacaoService{

    private final EnsaioApresentacaoRepository ensaioApresentacaoRepository;

    public EnsaioApresentacaoServiceImpl(EnsaioApresentacaoRepository ensaioApresentacaoRepository) {
        this.ensaioApresentacaoRepository = ensaioApresentacaoRepository;
    }

    @Override
    public EnsaioApresentacaoDTO salvar(EnsaioApresentacaoDTO dto) throws Exception{
        EnsaioApresentacao entity = new EnsaioApresentacao();
        try{
            entity = ensaioApresentacaoRepository.save(EnsaioApresentacaoMapper.toEntity(dto));
            return EnsaioApresentacaoMapper.toDto(entity);
        }
        catch(Exception e){
            throw new Exception("Erro ao salvar ensaio apresentacao");
        }
    }

    @Override
    public List<EnsaioApresentacaoDTO> buscar(){
        List<EnsaioApresentacaoDTO> dtos = new ArrayList<>();
        List<EnsaioApresentacao> ensaioApresentacaoList = ensaioApresentacaoRepository.findAll();
        for (EnsaioApresentacao entity : ensaioApresentacaoList) {
            dtos.add(EnsaioApresentacaoMapper.toDto(entity));
        }
        return dtos;
    }

    @Override
    public void deletar (Long id){
        ensaioApresentacaoRepository.deleteById(id);
    }
}