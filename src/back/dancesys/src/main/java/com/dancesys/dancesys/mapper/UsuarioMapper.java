package com.dancesys.dancesys.mapper;

import com.dancesys.dancesys.dto.LoginDTO;
import com.dancesys.dancesys.dto.UsuarioDTO;
import com.dancesys.dancesys.entity.Usuario;

public class UsuarioMapper {
        public static Usuario toEntity(UsuarioDTO dto) {
            if (dto == null) return null;

            Usuario entity = new Usuario();

            entity.setId(dto.getId());
            entity.setNome(dto.getNome());
            entity.setCpf(dto.getCpf());
            entity.setNumero(dto.getNumero());
            entity.setEmail(dto.getEmail());
            entity.setSenha(dto.getSenha());
            entity.setTipo(dto.getTipo());
            if(dto.getStatus().equals(true)){
                entity.setStatus(Usuario.ativo);
            }else if(dto.getStatus().equals(false)){
                entity.setStatus(Usuario.desativo);
            }
            entity.setEndereco(dto.getEndereco());
            entity.setUrlFoto(dto.getUrlFoto());
            entity.setDataNascimento(dto.getDataNascimento());
            entity.setCriadoEm(dto.getCriadoEm());

            return entity;
        }

        public static UsuarioDTO toDTO(Usuario entity) {
            if (entity == null) return null;

            UsuarioDTO dto = new UsuarioDTO();

            dto.setId(entity.getId());
            dto.setNome(entity.getNome());
            dto.setCpf(entity.getCpf());
            dto.setNumero(entity.getNumero());
            dto.setEmail(entity.getEmail());
            dto.setSenha(entity.getSenha());
            dto.setTipo(entity.getTipo());
            if(entity.getStatus().equals(Usuario.ativo)){
                dto.setStatus(true);
            }else if(entity.getStatus().equals(Usuario.desativo)){
                dto.setStatus(false);
            }
            dto.setEndereco(entity.getEndereco());
            dto.setUrlFoto(entity.getUrlFoto());
            dto.setDataNascimento(entity.getDataNascimento());
            dto.setCriadoEm(entity.getCriadoEm());

            return dto;
        }

        public static LoginDTO toLoginDTO(Usuario entity) {
            if (entity == null) return null;

            LoginDTO dto = new LoginDTO();

            dto.setId(entity.getId());
            dto.setNome(entity.getNome());
            if(entity.getStatus().equals(Usuario.ativo)){
                dto.setStatus(true);
            }else{
                dto.setStatus(false);
            }
            dto.setTipo(entity.getTipo());
            dto.setUrlFoto(entity.getUrlFoto());

            return dto;
        }
}
