package com.dancesys.dancesys.service;


import com.dancesys.dancesys.dto.ChamadaAulaDto;
import com.dancesys.dancesys.entity.ChamadaAula;
import com.dancesys.dancesys.mapper.ChamadaAulaMapper;
import com.dancesys.dancesys.repository.ChamadaAulaRepository;
import org.springframework.stereotype.Service;

@Service
public class ChamadaAulaServiceImpl implements ChamadaAulaService {
    private ChamadaAulaRepository chamadaAulaRepository;
    public ChamadaAulaServiceImpl(ChamadaAulaRepository chamadaAulaRepository) {
        this.chamadaAulaRepository = chamadaAulaRepository;
    }

    @Override
    public ChamadaAulaDto salvar(ChamadaAulaDto dto) throws Exception{
        ChamadaAula chamada = new ChamadaAula();

        try{
            if(dto.getId() == null){
                dto.setPresenca(ChamadaAula.faltante);
            }
            chamada = chamadaAulaRepository.save(ChamadaAulaMapper.toEntity(dto));
            ChamadaAulaDto newDTO = ChamadaAulaMapper.toDto(chamada);
            return newDTO;
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public ChamadaAulaDto mudarStatus(ChamadaAulaDto dto) throws Exception{
        if(dto.getId().equals(ChamadaAula.faltante)){
            dto.setPresenca(ChamadaAula.presente);
        }else{
            dto.setPresenca(ChamadaAula.faltante);
        }

        return salvar(dto);
    }
}
