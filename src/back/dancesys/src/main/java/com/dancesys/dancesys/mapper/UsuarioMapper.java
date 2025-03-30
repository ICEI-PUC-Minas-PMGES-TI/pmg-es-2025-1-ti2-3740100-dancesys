package com.dancesys.dancesys.mapper;

import com.dancesys.dancesys.dto.LoginDto;
import com.dancesys.dancesys.entity.Usuario;
import com.dancesys.dancesys.dto.UsuarioDto;

public class UsuarioMapper {

    public static UsuarioDto toDto(Usuario u){
        if(u == null) return null;
        UsuarioDto dto = new UsuarioDto();
        dto.setId(u.getId());
        dto.setNome(u.getNome());
        dto.setCpf(u.getCpf());
        dto.setNumero(u.getNumero());
        dto.setEmail(u.getEmail());
        dto.setSenha(u.getSenha());
        dto.setEnumTipo(u.getEnumTipo());
        dto.setCreditos(u.getCreditos());
        dto.setStatus(u.getStatus());
        dto.setUrlFoto(u.getUrlFoto());
        dto.setDataNascimento(u.getDataNascimento());
        dto.setCriadoEm(u.getCriadoEm());
        dto.setValorHoraExtra(u.getValorHoraExtra());
        return dto;
    }

    public static Usuario toEntity(UsuarioDto u){
        if(u == null) return null;
        Usuario entity = new Usuario();
        entity.setId(u.getId());
        entity.setNome(u.getNome());
        entity.setCpf(u.getCpf());
        entity.setNumero(u.getNumero());
        entity.setEmail(u.getEmail());
        entity.setSenha(u.getSenha());
        entity.setEnumTipo(u.getEnumTipo());
        entity.setCreditos(u.getCreditos());
        entity.setStatus(u.getStatus());
        entity.setUrlFoto(u.getUrlFoto());
        entity.setDataNascimento(u.getDataNascimento());
        entity.setCriadoEm(u.getCriadoEm());
        entity.setValorHoraExtra(u.getValorHoraExtra());

        return entity;
    }

    public static LoginDto toLoginDto(Usuario u){
        if(u == null) return null;

        LoginDto dto = new LoginDto();

        dto.setId(u.getId());
        dto.setEnumTipo(u.getEnumTipo());
        dto.setStatus(u.getStatus());
        dto.setUrlFoto(u.getUrlFoto());

        return dto;
    }
}
