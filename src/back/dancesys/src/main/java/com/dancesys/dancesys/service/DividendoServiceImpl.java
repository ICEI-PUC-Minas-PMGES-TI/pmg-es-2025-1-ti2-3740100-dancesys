package com.dancesys.dancesys.service;


import com.dancesys.dancesys.dto.DividendoDTO;
import com.dancesys.dancesys.entity.Aluno;
import com.dancesys.dancesys.entity.Dividendo;
import com.dancesys.dancesys.mapper.DividendoMapper;
import com.dancesys.dancesys.repository.DividendoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class DividendoServiceImpl implements DividendoService {
    private final DividendoRepository dividendoRepository;

    public DividendoServiceImpl(DividendoRepository dividendoRepository) {
        this.dividendoRepository = dividendoRepository;
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
            return DividendoMapper.toDto(entity);
        }catch(Exception e){
            throw new Exception(e.getMessage());
        }
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

    public DividendoDTO gerarMensalidade(Aluno entity) throws Exception{
        DividendoDTO dto = new DividendoDTO();
        dto.setIdAluno(entity);
        dto.setValor(Dividendo.VALOR_MENSALIDADE);
        dto.setTipo(Dividendo.MENSALIDADE);
        String codigo = String.format("%d.%.2f.%02d%02d%d",
                Dividendo.MENSALIDADE, Dividendo.VALOR_MENSALIDADE, LocalDate.now().getDayOfMonth(), LocalDate.now().getMonthValue(), LocalDate.now().getYear());
        dto.setCodigo(codigo);

        return salvar(dto);
    }

    @Override
    public List<DividendoDTO> buscar(){
        List<DividendoDTO> dtos = new ArrayList<>();
        List<Dividendo> dividendos = dividendoRepository.findAll();
        for(Dividendo entity : dividendos){
            dtos.add(DividendoMapper.toDto(entity));
        }

        return dtos;
    }

    @Override
    public String deletar(Long id){
        dividendoRepository.deleteById(id);
        return "Dividendo excluida com Sucesso!";
    }
}
