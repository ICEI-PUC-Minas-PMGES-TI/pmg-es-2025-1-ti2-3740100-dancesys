package com.dancesys.dancesys.service;

import com.dancesys.dancesys.dto.ApresentacaoEventoDTO;
import com.dancesys.dancesys.dto.ApresentacaoFilter;
import com.dancesys.dancesys.dto.IngressoEventoDTO;
import com.dancesys.dancesys.entity.ApresentacaoEvento;
import com.dancesys.dancesys.entity.IngressoEvento;
import com.dancesys.dancesys.infra.PaginatedResponse;
import com.dancesys.dancesys.mapper.ApresentacaoEventoMapper;
import com.dancesys.dancesys.mapper.IngressoEventoMapper;
import com.dancesys.dancesys.repository.ApresentacaoEventoRepository;
import com.dancesys.dancesys.repository.ApresentacaoEventoRepositoryCustom;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service

public class ApresentacaoEventoServiceImpl implements  ApresentacaoEventoService {
    private final ApresentacaoEventoRepository apresentacaoEventoRepository;
    private final ApresentacaoAlunoServiceImpl apresentacaoAlunoServiceImpl;
    private final ApresentacaoEventoRepositoryCustom apresentacaoEventoRepositoryCustom;

    public ApresentacaoEventoServiceImpl(ApresentacaoEventoRepository apresentacaoEventoRepository, ApresentacaoAlunoServiceImpl apresentacaoAlunoServiceImpl, ApresentacaoEventoRepositoryCustom apresentacaoEventoRepositoryCustom) {
        this.apresentacaoEventoRepository = apresentacaoEventoRepository;
        this.apresentacaoAlunoServiceImpl = apresentacaoAlunoServiceImpl;
        this.apresentacaoEventoRepositoryCustom = apresentacaoEventoRepositoryCustom;
    }

    @Override
    public ApresentacaoEventoDTO salvar(ApresentacaoEventoDTO dto) throws Exception{
        ApresentacaoEvento entity = new ApresentacaoEvento();
        try{
            entity = apresentacaoEventoRepository.save(ApresentacaoEventoMapper.toEntity(dto));
            if(dto.getAlunos()!=null){
                for(Long idAluno: dto.getAlunos()){
                    apresentacaoAlunoServiceImpl.salvar(entity.getId(),idAluno);
                }
            }
            return ApresentacaoEventoMapper.toDto(entity);
        }
        catch(Exception e){
            throw new Exception("Erro ao salvar apresentacao evento");
        }
    }

    @Override
    public PaginatedResponse<ApresentacaoEvento> buscar(ApresentacaoFilter filtro){
        return apresentacaoEventoRepositoryCustom.buscar(filtro);
    }

    @Override
    public void deletar (Long id){
        apresentacaoEventoRepository.deleteById(id);
    }


    public boolean existsByEvento(Long idEvento){
        return apresentacaoEventoRepository.existsByIdEvento_Id(idEvento);
    }


}
