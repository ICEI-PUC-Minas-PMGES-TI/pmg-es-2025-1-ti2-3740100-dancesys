package com.dancesys.dancesys.service;

import com.dancesys.dancesys.dto.IngressoEventoDTO;
import com.dancesys.dancesys.entity.IngressoEvento;
import com.dancesys.dancesys.mapper.IngressoEventoMapper;
import com.dancesys.dancesys.repository.IngressoEventoRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service

public class IngressoEventoServiceImpl implements IngressoEventoService {

    private final IngressoEventoRepository ingressoEventoRepository;

    public IngressoEventoServiceImpl(IngressoEventoRepository ingressoEventoRepository) {
        this.ingressoEventoRepository = ingressoEventoRepository;
    }

    @Override
    public IngressoEventoDTO salvar(IngressoEventoDTO dto) throws Exception{
        IngressoEvento entity = new IngressoEvento();
        try{
            entity = ingressoEventoRepository.save(IngressoEventoMapper.toEntity(dto));
            return IngressoEventoMapper.toDto(entity);
        }
        catch(Exception e){
            throw new Exception("Erro ao salvar ingresso evento");
        }
    }

    @Override
    public List<IngressoEventoDTO> buscar(){
        List<IngressoEventoDTO> dtos = new ArrayList<>();
        List<IngressoEvento> ingressoEventoList = ingressoEventoRepository.findAll();
        for (IngressoEvento entity : ingressoEventoList) {
            dtos.add(IngressoEventoMapper.toDto(entity));
        }
        return dtos;
    }

    @Override
    public void deletar (Long id){
        ingressoEventoRepository.deleteById(id);
    }
}
