package com.dancesys.dancesys.service;

import com.dancesys.dancesys.dto.ApresentacaoEventoDTO;
import com.dancesys.dancesys.dto.IngressoEventoDTO;
import com.dancesys.dancesys.entity.ApresentacaoEvento;
import com.dancesys.dancesys.entity.IngressoEvento;
import com.dancesys.dancesys.mapper.ApresentacaoEventoMapper;
import com.dancesys.dancesys.mapper.IngressoEventoMapper;
import com.dancesys.dancesys.repository.ApresentacaoEventoRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service

public class ApresentacaoEventoServiceImpl implements  ApresentacaoEventoService {
    private final ApresentacaoEventoRepository apresentacaoEventoRepository;

    public ApresentacaoEventoServiceImpl(ApresentacaoEventoRepository apresentacaoEventoRepository) {
        this.apresentacaoEventoRepository = apresentacaoEventoRepository;
    }

    @Override
    public ApresentacaoEventoDTO salvar(ApresentacaoEventoDTO dto) throws Exception{
        ApresentacaoEvento entity = new ApresentacaoEvento();
        try{
            entity = apresentacaoEventoRepository.save(ApresentacaoEventoMapper.toEntity(dto));
            return ApresentacaoEventoMapper.toDto(entity);
        }
        catch(Exception e){
            throw new Exception("Erro ao salvar apresentacao evento");
        }
    }

    @Override
    public List<ApresentacaoEventoDTO> buscar(){
        List<ApresentacaoEventoDTO> dtos = new ArrayList<>();
        List<ApresentacaoEvento> apresentacaoEventoList = apresentacaoEventoRepository.findAll();
        for (ApresentacaoEvento entity : apresentacaoEventoList) {
            dtos.add(ApresentacaoEventoMapper.toDto(entity));
        }
        return dtos;
    }

    @Override
    public void deletar (Long id){
        apresentacaoEventoRepository.deleteById(id);
    }


    public boolean existsByEvento(Long idEvento){
        return apresentacaoEventoRepository.existsByIdEvento_Id(idEvento);
    }


}
