package com.dancesys.dancesys.service;

import com.dancesys.dancesys.dto.AlunoDTO;
import com.dancesys.dancesys.dto.LoginDTO;
import com.dancesys.dancesys.dto.UsuarioDTO;
import com.dancesys.dancesys.entity.Aluno;
import com.dancesys.dancesys.entity.Usuario;
import com.dancesys.dancesys.mapper.AlunoMapper;
import com.dancesys.dancesys.mapper.UsuarioMapper;
import com.dancesys.dancesys.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class UsuarioServiceImpl implements UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final AlunoServiceImpl alunoServiceImpl;

    public UsuarioServiceImpl(
            UsuarioRepository usuarioRepository,
            AlunoServiceImpl alunoServiceImpl
    ) {
        this.usuarioRepository = usuarioRepository;
        this.alunoServiceImpl = alunoServiceImpl;
    }

    @Override
    public UsuarioDTO salvar(UsuarioDTO dto) throws Exception{
        Usuario usuario = new Usuario();
        try {
            if(dto.getId()==null){
                dto.setStatus(true);
                dto.setCriadoEm(LocalDate.now());
            }
            usuario = usuarioRepository.save(UsuarioMapper.toEntity(dto));
            UsuarioDTO newDTO = UsuarioMapper.toDTO(usuario);
            return newDTO;
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public LoginDTO login(UsuarioDTO dto) throws Exception{
        try{
            Usuario user = usuarioRepository.findByEmailAndSenha(dto.getEmail(), dto.getSenha());
            if(user==null){
                throw new Exception("Usuario não encontrado");
            }

            if(user.getStatus().equals(Usuario.desativo)){
                throw new Exception("Usuario desativado");
            }
            return UsuarioMapper.toLoginDTO(user);
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

//    public AlunoDTO salvarAluno(AlunoDTO dto) throws Exception{
//        try{
//            Aluno aluno = alunoServiceImpl.salvar(AlunoMapper.toEntity(dto));
//        }
//    }
}
