package com.dancesys.dancesys.service;


import com.dancesys.dancesys.dto.DividendoDTO;
import com.dancesys.dancesys.dto.DividendoFilter;
import com.dancesys.dancesys.entity.Aluno;
import com.dancesys.dancesys.entity.Dividendo;
import com.dancesys.dancesys.entity.Evento;
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

    public DividendoServiceImpl(DividendoRepository dividendoRepository, DividendoRepositoryCustom dividendoRepositoryCustom, EmailServiceImpl emailServiceImpl) {
        this.dividendoRepository = dividendoRepository;
        this.dividendoRepositoryCustom = dividendoRepositoryCustom;
        this.emailServiceImpl = emailServiceImpl;
    }

    @Override
    public DividendoDTO salvar(DividendoDTO dto) throws Exception{
        Dividendo entity = new Dividendo();
        try{
            if(dto.getId() == null){
                dto.setCriadoEm(LocalDate.now());
                dto.setStatus(Dividendo.pendente);
            }

            entity = dividendoRepository.save(DividendoMapper.toEntity(dto));
            emailServiceImpl.enviarEmailHtml(entity.getIdAluno().getIdUsuario().getEmail(), definirTipo(entity.getTipo()), entity.getValor().toString());
            return DividendoMapper.toDto(entity);
        }catch(Exception e){
            throw new Exception(e.getMessage());
        }
    }

    private String definirTipo(Integer tipo){
        switch (tipo){
            case 1:
                return "Matricula";
            case 2:
                return "Mensalidade";
            case 3:
                return "Aula";
            case 4:
                return "Ingresso";
            case 5:
                return "Figurino";
        }

        return null;
    }

    public DividendoDTO gerarMatricula(Aluno entity) throws Exception{
        DividendoDTO dto = new DividendoDTO();
        dto.setIdAluno(entity);
        dto.setValor(Dividendo.VALOR_MATRICULA);
        dto.setTipo(Dividendo.MATRICULA);
        String codigo = String.format("%d.%.2f.%02d%02d%d",
                Dividendo.MATRICULA, Dividendo.VALOR_MATRICULA, LocalDate.now().getDayOfMonth(), LocalDate.now().getMonthValue(), LocalDate.now().getYear());
        dto.setCodigo(codigo);

        return salvar(dto);
    }

    public DividendoDTO gerarMensalidadeFixo(Aluno entity) throws Exception{
        DividendoDTO dto = new DividendoDTO();
        dto.setIdAluno(entity);
        dto.setValor(Dividendo.VALOR_MENSALIDADE_FIXO);
        dto.setTipo(Dividendo.MENSALIDADE);
        String codigo = String.format("%d.%.2f.%02d%02d%d",
                Dividendo.MENSALIDADE, Dividendo.VALOR_MENSALIDADE_FIXO, LocalDate.now().getDayOfMonth(), LocalDate.now().getMonthValue(), LocalDate.now().getYear());
        dto.setCodigo(codigo);

        return salvar(dto);
    }

    public DividendoDTO gerarMensalidadeFlexivel(Aluno entity) throws Exception{
        DividendoDTO dto = new DividendoDTO();
        dto.setIdAluno(entity);
        dto.setValor(Dividendo.VALOR_MENSALIDADE_FLEXIVEL);
        dto.setTipo(Dividendo.MENSALIDADE);
        String codigo = String.format("%d.%.2f.%02d%02d%d",
                Dividendo.MENSALIDADE, Dividendo.VALOR_MENSALIDADE_FLEXIVEL, LocalDate.now().getDayOfMonth(), LocalDate.now().getMonthValue(), LocalDate.now().getYear());
        dto.setCodigo(codigo);

        return salvar(dto);
    }

    public void gerarMensalidade(Aluno entity) throws Exception{
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
    public DividendoDTO pagar(Long id) throws Exception{
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
        LocalDate date = data.minusMonths(1);
        List<Dividendo> dividendosAtrasados = dividendoRepository.findByCriadoEmLessThanEqualAndStatusEquals(date, Dividendo.pendente);

        for(Dividendo d : dividendosAtrasados){
            d.setStatus(Dividendo.atrasado);
            dividendoRepository.save(d);
        }
    }
}
