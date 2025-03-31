package com.dancesys.dancesys.service;


import com.dancesys.dancesys.dto.UsuarioDto;
import com.dancesys.dancesys.entity.Usuario;
import com.dancesys.dancesys.mapper.UsuarioMapper;
import com.dancesys.dancesys.repository.ChamadaAulaRepository;
import org.springframework.stereotype.Service;

@Service
public class ChamadaAulaServiceImpl {
    private ChamadaAulaRepository chamadaAulaRepository;
    public ChamadaAulaServiceImpl(ChamadaAulaRepository chamadaAulaRepository) {
        this.chamadaAulaRepository = chamadaAulaRepository;
    }

}
