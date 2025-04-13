package com.dancesys.dancesys.mapper;

import com.dancesys.dancesys.dto.AlunoDTO;
import com.dancesys.dancesys.dto.ModalidadeAlunoNivelDTO;
import com.dancesys.dancesys.dto.UsuarioDTO;
import com.dancesys.dancesys.entity.Aluno;
import com.dancesys.dancesys.entity.Usuario;

import java.util.List;

public class AlunoMapper {
    public static Aluno toEntity(AlunoDTO dto){
        if(dto == null) return null;

        Aluno entity = new Aluno();
        Usuario user = new Usuario();

        entity.setId(dto.getId());
        entity.setCreditos(dto.getCreditos());
        entity.setTipo(dto.getTipo());
        entity.setBoolBaile(dto.getBoolBaile());
        user.setId(dto.getIdUsuario());
        entity.setIdUsuario(user);

        return entity;
    }

    public static AlunoDTO toDto(Aluno entity){
       if(entity == null) return null;

       AlunoDTO dto = new AlunoDTO();

       dto.setId(entity.getId());
       dto.setNome(entity.getIdUsuario().getNome());
       dto.setCpf(entity.getIdUsuario().getCpf());
       dto.setNumero(entity.getIdUsuario().getNumero());
       dto.setEmail(entity.getIdUsuario().getEmail());
       dto.setSenha(entity.getIdUsuario().getSenha());
       dto.setTipo(entity.getIdUsuario().getTipo());
       dto.setEndereco(entity.getIdUsuario().getEndereco());
       dto.setUrlFoto(entity.getIdUsuario().getUrlFoto());
       dto.setDataNascimento(entity.getIdUsuario().getDataNascimento());
       dto.setCriadoEm(entity.getIdUsuario().getCriadoEm());
       dto.setCreditos(entity.getCreditos());
       dto.setBoolBaile(entity.getBoolBaile());
       dto.setTipoAluno(entity.getTipo());
       dto.setIdUsuario(entity.getIdUsuario().getId());

       return dto;
    }

    public static AlunoDTO allToDTO(UsuarioDTO user, Aluno aluno, List<ModalidadeAlunoNivelDTO> modList){
        AlunoDTO dto = new AlunoDTO();

        dto.setId(aluno.getId());
        dto.setNome(user.getNome());
        dto.setCpf(user.getCpf());
        dto.setEmail(user.getEmail());
        dto.setSenha(user.getSenha());
        dto.setTipo(user.getTipo());
        dto.setEndereco(user.getEndereco());
        dto.setStatus(user.getStatus());
        dto.setDataNascimento(user.getDataNascimento());
        dto.setCriadoEm(user.getCriadoEm());
        dto.setUrlFoto(user.getUrlFoto());
        dto.setCreditos(aluno.getCreditos());
        dto.setBoolBaile(aluno.getBoolBaile());
        dto.setTipoAluno(aluno.getTipo());
        dto.setIdUsuario(UsuarioMapper.toEntity(user).getId());
        dto.setModalidades(modList);

        return  dto;
    }
}
