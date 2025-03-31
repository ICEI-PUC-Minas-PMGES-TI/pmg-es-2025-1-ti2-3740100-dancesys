package com.dancesys.dancesys.service;


import com.dancesys.dancesys.dto.ChamadaAulaDto;
import com.dancesys.dancesys.dto.UsuarioDto;
import com.dancesys.dancesys.entity.ChamadaAula;
import com.dancesys.dancesys.entity.Usuario;
import com.dancesys.dancesys.mapper.ChamadaAulaMapper;
import com.dancesys.dancesys.mapper.UsuarioMapper;
import com.dancesys.dancesys.repository.ChamadaAulaRepository;
import org.springframework.stereotype.Service;

@Service
public class ChamadaAulaServiceImpl {
    private ChamadaAulaRepository chamadaAulaRepository;
    public ChamadaAulaServiceImpl(ChamadaAulaRepository chamadaAulaRepository) {
        this.chamadaAulaRepository = chamadaAulaRepository;
    }

    public ChamadaAulaDto salvar(ChamadaAulaDto dto) throws Exception{
        ChamadaAula chamada = new ChamadaAula();

        try{
            if(dto.getId() == null){
                dto.setPresenca(Usuario.ativo);
            }
            chamada = chamadaAulaRepository.save(ChamadaAulaMapper.toEntity(dto));
            ChamadaAulaDto newDTO = ChamadaAulaMapper.toDto(chamada);
            return newDTO;
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }
}
