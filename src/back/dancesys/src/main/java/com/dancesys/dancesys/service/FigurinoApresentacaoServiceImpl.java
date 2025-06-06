package com.dancesys.dancesys.service;

import com.dancesys.dancesys.dto.DividendoDTO;
import com.dancesys.dancesys.dto.FigurinoAlunoFilter;
import com.dancesys.dancesys.dto.FigurinoApresentacaoDTO;
import com.dancesys.dancesys.entity.Dividendo;
import com.dancesys.dancesys.entity.Figurino;
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
    private final FigurinoServiceImpl figurinoService;

    public FigurinoApresentacaoServiceImpl(
            FigurinoApresentacaoRepository figurinoApresentacaoRepository,
            FigurinoAlunoRepositoryCustom figurinoAlunoRepository,
            DividendoServiceImpl dividendoService,
            FigurinoServiceImpl figurinoService
    ) {
        this.figurinoApresentacaoRepository = figurinoApresentacaoRepository;
        this.figurinoAlunoRepository = figurinoAlunoRepository;
        this.dividendoService = dividendoService;
        this.figurinoService = figurinoService;
    }

    @Override
    public FigurinoApresentacaoDTO salvar (FigurinoApresentacaoDTO dto) throws Exception {
        FigurinoApresentacao entity = new FigurinoApresentacao();
        try {
            if(dto.getId() == null){
                dto.setStatus(FigurinoApresentacao.ESTOQUE);

                Figurino figurino = figurinoService.findById(dto.getIdFigurino());
                String codigo = String.format("%d.%d.%d.%.2f.%02d%02d%d",
                        Dividendo.FIGURINO, dto.getIdAluno(), dto.getIdFigurino(), figurino.getValor(), LocalDate.now().getDayOfMonth(), LocalDate.now().getMonthValue(), LocalDate.now().getYear());
                dto.setCodigo(codigo);
            }

            entity = figurinoApresentacaoRepository.save(FigurinoApresentacaoMapper.toEntity(dto));

            if(dto.getId() == null){
                dividendoService.gerarFigurino(entity);
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
    public void deletar (Long id) throws RuntimeException {
        FigurinoApresentacao entity = figurinoApresentacaoRepository.findById(id).get();
        Dividendo dividendo = dividendoService.findByCodigo(entity.getCodigo());
        if(dividendo.getStatus().equals(Dividendo.pago)){
            throw new RuntimeException("Boleto ja pago!");
        }
        dividendoService.deletar(dividendo.getId());
        figurinoApresentacaoRepository.deleteById(id);
    }

    @Override
    public void toggleStatus(Long id) throws RuntimeException{
        FigurinoApresentacao entity = figurinoApresentacaoRepository.findById(id).get();
        if(entity.getStatus().equals(FigurinoApresentacao.ESTOQUE)){
            entity.setStatus(FigurinoApresentacao.ENTREGUE);
        }else if(entity.getStatus().equals(FigurinoApresentacao.ENTREGUE)){
            entity.setStatus(FigurinoApresentacao.DEVOLVIDO);
        }else{
            throw new RuntimeException("Ciclo completo!");
        }

        figurinoApresentacaoRepository.save(entity);
    }

}