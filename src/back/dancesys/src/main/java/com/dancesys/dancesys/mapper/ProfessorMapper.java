package com.dancesys.dancesys.mapper;

import com.dancesys.dancesys.dto.ProfessorDTO;
import com.dancesys.dancesys.dto.ProfessorModalidadeDTO;
import com.dancesys.dancesys.dto.UsuarioDTO;
import com.dancesys.dancesys.entity.Professor;

import java.util.List;

public class ProfessorMapper {
    public static Professor toEntity(ProfessorDTO dto){
        if(dto == null) return null;

        Professor entity = new Professor();

        entity.setId(dto.getId());
        entity.setInformacoesProfissionais(dto.getInformacoesProfissionais());
        entity.setValorHoraExtra(dto.getValorHoraExtra());

        return entity;
    }

    public static ProfessorDTO AlltoDto(UsuarioDTO uDto, Professor entity, List<Long> mods){
        ProfessorDTO dto = new ProfessorDTO();

        dto.setId(entity.getId());
        dto.setInformacoesProfissionais(entity.getInformacoesProfissionais());
        dto.setValorHoraExtra(entity.getValorHoraExtra());
        dto.setUsuario(entity.getIdUsuario());
        dto.setModalidades(mods);

        return dto;
    }
}
