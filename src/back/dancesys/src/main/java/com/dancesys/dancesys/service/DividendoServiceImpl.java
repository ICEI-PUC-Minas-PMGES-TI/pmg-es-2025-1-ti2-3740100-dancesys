package com.dancesys.dancesys.service;


import com.dancesys.dancesys.dto.DividendoDTO;
import com.dancesys.dancesys.dto.DividendoFilter;
import com.dancesys.dancesys.entity.*;
import com.dancesys.dancesys.infra.PaginatedResponse;
import com.dancesys.dancesys.mapper.DividendoMapper;
import com.dancesys.dancesys.repository.DividendoRepository;
import com.dancesys.dancesys.repository.DividendoRepositoryCustom;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class DividendoServiceImpl implements DividendoService {
    private final DividendoRepository dividendoRepository;
    private final DividendoRepositoryCustom dividendoRepositoryCustom;
    private final EmailServiceImpl emailServiceImpl;
    private final FigurinoServiceImpl figurinoServiceImpl;

    public DividendoServiceImpl(DividendoRepository dividendoRepository, DividendoRepositoryCustom dividendoRepositoryCustom, EmailServiceImpl emailServiceImpl, FigurinoServiceImpl figurinoServiceImpl) {
        this.dividendoRepository = dividendoRepository;
        this.dividendoRepositoryCustom = dividendoRepositoryCustom;
        this.emailServiceImpl = emailServiceImpl;
        this.figurinoServiceImpl = figurinoServiceImpl;
    }

    @Override
    public DividendoDTO salvar(DividendoDTO dto) throws RuntimeException{
        Dividendo entity = new Dividendo();
        try{
            if(dto.getId() == null){
                dto.setCriadoEm(LocalDate.now());
                dto.setStatus(Dividendo.pendente);
            }

            entity = dividendoRepository.save(DividendoMapper.toEntity(dto));
            return DividendoMapper.toDto(entity);
        }catch(Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    public DividendoDTO gerarMatricula(Aluno entity) throws RuntimeException{
        DividendoDTO dto = new DividendoDTO();
        dto.setIdAluno(entity);
        dto.setValor(Dividendo.VALOR_MATRICULA);
        dto.setTipo(Dividendo.MATRICULA);
        String codigo = String.format("%d.%.2f.%02d%02d%d",
                Dividendo.MATRICULA, Dividendo.VALOR_MATRICULA, LocalDate.now().getDayOfMonth(), LocalDate.now().getMonthValue(), LocalDate.now().getYear());
        dto.setCodigo(codigo);

        return salvar(dto);
    }

    public DividendoDTO gerarMensalidadeFixo(Aluno entity) throws RuntimeException{
        DividendoDTO dto = new DividendoDTO();
        dto.setIdAluno(entity);
        dto.setValor(Dividendo.VALOR_MENSALIDADE_FIXO);
        dto.setTipo(Dividendo.MENSALIDADE);
        String codigo = String.format("%d.%d.%.2f.%02d%02d%d",
                Dividendo.MENSALIDADE, entity.getId(), Dividendo.VALOR_MENSALIDADE_FIXO, LocalDate.now().getDayOfMonth(), LocalDate.now().getMonthValue(), LocalDate.now().getYear());
        dto.setCodigo(codigo);

        return salvar(dto);
    }

    public DividendoDTO gerarMensalidadeFlexivel(Aluno entity) throws RuntimeException{
        DividendoDTO dto = new DividendoDTO();
        dto.setIdAluno(entity);
        dto.setValor(Dividendo.VALOR_MENSALIDADE_FLEXIVEL);
        dto.setTipo(Dividendo.MENSALIDADE);
        String codigo = String.format("%d.%d.%.2f.%02d%02d%d",
                Dividendo.MENSALIDADE,entity.getId() , Dividendo.VALOR_MENSALIDADE_FLEXIVEL, LocalDate.now().getDayOfMonth(), LocalDate.now().getMonthValue(), LocalDate.now().getYear());
        dto.setCodigo(codigo);

        return salvar(dto);
    }

    public DividendoDTO gerarFigurino(FigurinoApresentacao entity) throws RuntimeException{
        try{
            DividendoDTO dto = new DividendoDTO();
            dto.setIdAluno(entity.getIdAluno());
            Figurino figurino = figurinoServiceImpl.findById(entity.getIdFigurino().getId());
            dto.setValor(figurino.getValor());
            dto.setTipo(Dividendo.FIGURINO);
            dto.setCodigo(entity.getCodigo());

            return salvar(dto);
        }catch(RuntimeException e){
            throw new RuntimeException(e.getMessage());
        }
    }

    public void gerarMensalidade(Aluno entity) throws RuntimeException{
        if(entity.getTipo().equals(Aluno.fixo)){
            gerarMensalidadeFixo(entity);
        }else{
            gerarMensalidadeFlexivel(entity);
        }
    }

    @Override
    public PaginatedResponse<Dividendo> buscar(DividendoFilter filtro){
        return dividendoRepositoryCustom.buscar(filtro);
    }

    @Override
    public DividendoDTO pagar(Long id) throws RuntimeException{
        Dividendo entity = dividendoRepository.findById(id).get();
        entity.setStatus(Dividendo.pago);
        entity.setPagoEm(LocalDate.now());
        return salvar(DividendoMapper.toDto(entity));
    }

    @Override
    public String deletar(Long id){
        dividendoRepository.deleteById(id);
        return "Dividendo excluida com Sucesso!";
    }

    @Override
    public void jobDividendosDiario(){
        LocalDate data = LocalDate.now();
        LocalDate date = data.minusDays(30);
        List<Dividendo> dividendosAtrasados = dividendoRepository.findByCriadoEmLessThanEqualAndStatusEquals(date, Dividendo.pendente);

        for(Dividendo d : dividendosAtrasados){
            d.setStatus(Dividendo.atrasado);
            dividendoRepository.save(d);
        }
    }

    public Dividendo findByCodigo(String codigo){
        return dividendoRepository.findByCodigo(codigo).get();
    }
}
