package com.dancesys.dancesys.service;

import com.dancesys.dancesys.dto.EnsaioApresentacaoDTO;
import com.dancesys.dancesys.dto.EnsaioFilter;
import com.dancesys.dancesys.entity.EnsaioApresentacao;
import com.dancesys.dancesys.infra.PaginatedResponse;
import com.dancesys.dancesys.mapper.EnsaioApresentacaoMapper;
import com.dancesys.dancesys.repository.EnsaioApresentacaoRepository;
import com.dancesys.dancesys.repository.EnsaioRepositoryCustom;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EnsaioApresentacaoServiceImpl implements EnsaioApresentacaoService{

    private final EnsaioApresentacaoRepository ensaioApresentacaoRepository;
    private final EnsaioAlunoServiceImpl ensaioAlunoServiceImpl;
    private final EnsaioRepositoryCustom ensaioRepositoryCustom;

    public EnsaioApresentacaoServiceImpl(EnsaioApresentacaoRepository ensaioApresentacaoRepository, EnsaioAlunoServiceImpl ensaioAlunoServiceImpl, EnsaioRepositoryCustom ensaioRepositoryCustom) {
        this.ensaioApresentacaoRepository = ensaioApresentacaoRepository;
        this.ensaioAlunoServiceImpl = ensaioAlunoServiceImpl;
        this.ensaioRepositoryCustom = ensaioRepositoryCustom;
    }

    @Override
    public EnsaioApresentacaoDTO salvar(EnsaioApresentacaoDTO dto) throws Exception{
        EnsaioApresentacao entity = new EnsaioApresentacao();
        try{
            entity = ensaioApresentacaoRepository.save(EnsaioApresentacaoMapper.toEntity(dto));
            for(Long idAluno : dto.getAlunos()){
                ensaioAlunoServiceImpl.salvar(entity.getId(),idAluno);
            }
            return EnsaioApresentacaoMapper.toDto(entity);
        }
        catch(Exception e){
            throw new Exception("Erro ao salvar ensaio apresentacao");
        }
    }

    @Override
    public PaginatedResponse<EnsaioApresentacao> buscar(EnsaioFilter filtro){
       return ensaioRepositoryCustom.buscar(filtro);
    }

    @Override
    public void deletar (Long id){
        ensaioApresentacaoRepository.deleteById(id);
    }

    public boolean existsByApresentacaoId(Long id){
        return ensaioApresentacaoRepository.existsByIdApresentacaoEventoId(id);
    }
}