package com.dancesys.dancesys.service;

import com.dancesys.dancesys.dto.EnsaioApresentacaoDTO;
import com.dancesys.dancesys.entity.EnsaioApresentacao;
import com.dancesys.dancesys.mapper.EnsaioApresentacaoMapper;
import com.dancesys.dancesys.repository.EnsaioApresentacaoRepository;
import org.springframework.stereotype.Service;

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
}