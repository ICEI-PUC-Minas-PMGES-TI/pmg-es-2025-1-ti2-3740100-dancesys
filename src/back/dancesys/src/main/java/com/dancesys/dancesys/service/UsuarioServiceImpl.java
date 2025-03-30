package com.dancesys.dancesys.service;

import com.dancesys.dancesys.dto.LoginDto;
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
            if(dto.getNome() == null){
                dto.setStatus(Usuario.ativo);
            }
            usuario = usuarioRepository.save(UsuarioMapper.toEntity(dto));
            UsuarioDto newDTO = UsuarioMapper.toDto(usuario);
            return newDTO;
        }catch(Exception e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public LoginDto login(UsuarioDto dto) throws Exception {
        try {
            Optional<Usuario> user = usuarioRepository.findByEmailAndSenha(dto.getEmail(), dto.getSenha());

            if (user.isPresent()) {
                if(user.get().getStatus().equals(Usuario.desativo)){
                    throw new RuntimeException("Usuário desabilitado!");
                }else{
                    return UsuarioMapper.toLoginDto(user.get());
                }
            }

            throw new RuntimeException("Usuário não encontrado ou senha inválida!");

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public UsuarioDto mudarStatus(Long id) throws Exception{
        try{
            Usuario user =  usuarioRepository.findById(id).get();
            if(user != null){
                if(user.getStatus().equals(Usuario.ativo)){
                    user.setStatus(Usuario.desativo);
                }else{
                    user.setStatus(Usuario.ativo);
                }
            }
            UsuarioDto newDto = UsuarioMapper.toDto(user);
            return salvar(newDto);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Usuario aumentarCreditos(Usuario u, Integer creditos) {
        u.setCreditos(u.getCreditos()+creditos);
        return u;
    }

    private Usuario diminuirCreditos(Usuario u, Integer creditos) {
        u.setCreditos(u.getCreditos()-creditos);
        return u;
    }

    private Usuario resetarPontos(Usuario u){
        u.setCreditos(Usuario.max_creditos);
        return u;
    }
}
