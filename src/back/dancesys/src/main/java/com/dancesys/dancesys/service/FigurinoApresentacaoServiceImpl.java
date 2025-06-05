package com.dancesys.dancesys.service;

import com.dancesys.dancesys.dto.DividendoDTO;
import com.dancesys.dancesys.dto.FigurinoAlunoFilter;
import com.dancesys.dancesys.dto.FigurinoApresentacaoDTO;
import com.dancesys.dancesys.entity.Dividendo;
import com.dancesys.dancesys.entity.FigurinoApresentacao;
import com.dancesys.dancesys.infra.PaginatedResponse;
import com.dancesys.dancesys.mapper.FigurinoApresentacaoMapper;
import com.dancesys.dancesys.repository.FigurinoAlunoRepositoryCustom;
import com.dancesys.dancesys.repository.FigurinoApresentacaoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class FigurinoApresentacaoServiceImpl implements FigurinoApresentacaoService {

    private final FigurinoApresentacaoRepository figurinoApresentacaoRepository;
    private final FigurinoAlunoRepositoryCustom  figurinoAlunoRepository;
    private final DividendoServiceImpl dividendoService;

    public FigurinoApresentacaoServiceImpl(
            FigurinoApresentacaoRepository figurinoApresentacaoRepository,
            FigurinoAlunoRepositoryCustom figurinoAlunoRepository,
            DividendoServiceImpl dividendoService
    ) {
        this.figurinoApresentacaoRepository = figurinoApresentacaoRepository;
        this.figurinoAlunoRepository = figurinoAlunoRepository;
        this.dividendoService = dividendoService;
    }

    @Override
    public FigurinoApresentacaoDTO salvar (FigurinoApresentacaoDTO dto) throws Exception {
        FigurinoApresentacao entity = new FigurinoApresentacao();
        try {
            if(dto.getId() == null){
                dto.setStatus(FigurinoApresentacao.ESTOQUE);
                dto.setCodigo("a");
                DividendoDTO dDto = dividendoService.gerarFigurino(figurinoApresentacaoRepository.save(FigurinoApresentacaoMapper.toEntity(dto)));
                dto.setCodigo(dDto.getCodigo());
                entity = figurinoApresentacaoRepository.save(FigurinoApresentacaoMapper.toEntity(dto));
            }else{
                entity = figurinoApresentacaoRepository.save(FigurinoApresentacaoMapper.toEntity(dto));
            }

            return FigurinoApresentacaoMapper.toDto(entity);
        }
        catch (Exception e){
            throw new Exception("erro ao salvar figurino", e);
        }
    }

    @Override
    public PaginatedResponse<FigurinoApresentacao> buscar(FigurinoAlunoFilter filtro) {
        return figurinoAlunoRepository.buscar(filtro);
    }

    @Override
    public  void deletar (Long id) {
        figurinoApresentacaoRepository.deleteById(id);
    }

}