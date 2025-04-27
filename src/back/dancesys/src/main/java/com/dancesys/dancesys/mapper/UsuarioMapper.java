package com.dancesys.dancesys.mapper;

import com.dancesys.dancesys.dto.AlunoDTO;
import com.dancesys.dancesys.dto.LoginDTO;
import com.dancesys.dancesys.dto.ProfessorDTO;
import com.dancesys.dancesys.dto.UsuarioDTO;
import com.dancesys.dancesys.entity.Aluno;
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
            entity.setStatus(dto.getStatus());
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
            dto.setStatus(entity.getStatus());
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

        public static UsuarioDTO alunoDTOtoDto(AlunoDTO Adto){
            if (Adto == null) return null;

            UsuarioDTO dto = new UsuarioDTO();

            dto.setId(Adto.getIdUsuario());
            dto.setNome(Adto.getNome());
            dto.setCpf(Adto.getCpf());
            dto.setNumero(Adto.getNumero());
            dto.setEmail(Adto.getEmail());
            dto.setSenha(Adto.getSenha());
            dto.setTipo(Adto.getTipo());
            dto.setStatus(Adto.getStatus());
            dto.setEndereco(Adto.getEndereco());
            dto.setUrlFoto(Adto.getUrlFoto());
            dto.setDataNascimento(Adto.getDataNascimento());

            return dto;
        }

        public static UsuarioDTO professorDTOtoDto(ProfessorDTO pDto){
            if (pDto == null) return null;

            UsuarioDTO dto = new UsuarioDTO();

            dto.setId(pDto.getId());
            dto.setNome(pDto.getNome());
            dto.setCpf(pDto.getCpf());
            dto.setNumero(pDto.getNumero());
            dto.setEmail(pDto.getEmail());
            dto.setSenha(pDto.getSenha());
            dto.setTipo(pDto.getTipo());
            dto.setStatus(pDto.getStatus());
            dto.setEndereco(pDto.getEndereco());
            dto.setUrlFoto(pDto.getUrlFoto());
            dto.setDataNascimento(pDto.getDataNascimento());

            return dto;
        }
}
