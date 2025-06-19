package com.dancesys.dancesys.service;

import com.dancesys.dancesys.dto.IngressoEventoDTO;
import com.dancesys.dancesys.entity.Dividendo;
import com.dancesys.dancesys.entity.Evento;
import com.dancesys.dancesys.entity.IngressoEvento;
import com.dancesys.dancesys.mapper.IngressoEventoMapper;
import com.dancesys.dancesys.repository.IngressoEventoRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service

public class IngressoEventoServiceImpl implements IngressoEventoService {

    private final IngressoEventoRepository ingressoEventoRepository;
    private final EventoServiceImpl  eventoServiceImpl;
    private final DividendoServiceImpl dividendoServiceImpl;

    public IngressoEventoServiceImpl(
            IngressoEventoRepository ingressoEventoRepository,
            EventoServiceImpl eventoServiceImpl,
            DividendoServiceImpl dividendoServiceImpl
    ) {
        this.ingressoEventoRepository = ingressoEventoRepository;
        this.eventoServiceImpl = eventoServiceImpl;
        this.dividendoServiceImpl = dividendoServiceImpl;
    }

    @Override
    public IngressoEventoDTO salvar(IngressoEventoDTO dto) throws Exception{
        IngressoEvento entity = new IngressoEvento();
        try{
            if (dto.getId() == null) {
                Evento evt  = eventoServiceImpl.findById(dto.getIdEvento());
                BigDecimal valor = evt.getValor();
                if(dto.getTipo().equals(IngressoEvento.MEIA)){
                    BigDecimal div = new BigDecimal(2);
                    valor = valor.divide(div,2, RoundingMode.HALF_UP);
                }
                BigDecimal mult = new BigDecimal(dto.getQuantidade());
                BigDecimal total = valor.multiply(mult).setScale(2, RoundingMode.HALF_UP);

                String codigo = String.format("%d.%d.%d.%.2f.%02d%02d%d",
                        Dividendo.EVENTO, dto.getIdAluno(), evt.getId(), total, LocalDate.now().getDayOfMonth(), LocalDate.now().getMonthValue(), LocalDate.now().getYear());
                dto.setCodigo(codigo);

                dividendoServiceImpl.gerarIngresso(codigo,total, dto.getIdAluno());
            }
            entity = ingressoEventoRepository.save(IngressoEventoMapper.toEntity(dto));
            return IngressoEventoMapper.toDto(entity);
        }
        catch(Exception e){
            throw new Exception(e.getMessage());
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
