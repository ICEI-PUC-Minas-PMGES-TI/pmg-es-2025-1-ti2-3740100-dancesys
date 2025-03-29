package com.dancesys.dancesys.service;

import com.dancesys.dancesys.dto.UsuarioDto;
import com.dancesys.dancesys.entity.Usuario;
import com.dancesys.dancesys.mapper.UsuarioMapper;
import com.dancesys.dancesys.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioServiceImpl implements UsuarioService {
    private final UsuarioRepository usuarioRepository;

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UsuarioDto salvar(UsuarioDto dto) throws Exception{
        Usuario usuario = new Usuario();

        try{
            usuario = usuarioRepository.save(UsuarioMapper.toEntity(dto));
            UsuarioDto newDTO = UsuarioMapper.toDto(usuario);
            return newDTO;
        }catch(Exception e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public Usuario logar(UsuarioDto dto) throws Exception {
        try {
            Optional<Usuario> user = usuarioRepository.findByEmailAndSenha(dto.getEmail(), dto.getSenha());

            if (user.isPresent()) {
                return user.get();
            }

            throw new RuntimeException("Usuário não encontrado ou senha inválida.");

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Usuario mudarStatus(Long id) throws Exception{
        try{
            Usuario user =  usuarioRepository.findById(id).get();
            if(user != null){
                if(user.getStatus().equals(1)){
                    user.setStatus(0);
                }else{
                    user.setStatus(1);
                }
            }
            usuarioRepository.save(user);
            return user;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
