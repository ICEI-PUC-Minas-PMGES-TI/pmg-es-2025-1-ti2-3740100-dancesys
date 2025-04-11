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
        entity.setIdUsuario(dto.getUsuario());

        return entity;
    }

    public static ProfessorDTO AlltoDto(UsuarioDTO uDto, Professor entity, List<ProfessorModalidadeDTO> mods){
        ProfessorDTO dto = new ProfessorDTO();

        dto.setId(entity.getId());
        dto.setNome(uDto.getNome());
        dto.setCpf(uDto.getCpf());
        dto.setNumero(uDto.getNumero());
        dto.setEmail(uDto.getEmail());
        dto.setSenha(uDto.getSenha());
        dto.setTipo(uDto.getTipo());
        dto.setStatus(uDto.getStatus());
        dto.setEndereco(uDto.getEndereco());
        dto.setUrlFoto(uDto.getUrlFoto());
        dto.setDataNascimento(uDto.getDataNascimento());
        dto.setCriadoEm(uDto.getCriadoEm());
        dto.setInformacoesProfissionais(entity.getInformacoesProfissionais());
        dto.setValorHoraExtra(entity.getValorHoraExtra());
        dto.setUsuario(entity.getIdUsuario());
        dto.setIdUsuario(entity.getIdUsuario().getId());
        dto.setModalidades(mods);

        return dto;
    }
}
