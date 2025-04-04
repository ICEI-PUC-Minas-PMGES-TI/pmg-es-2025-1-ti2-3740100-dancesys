package com.dancesys.dancesys.service;

import com.dancesys.dancesys.dto.LoginDto;
import com.dancesys.dancesys.dto.UsuarioAlunoDto;
import com.dancesys.dancesys.dto.UsuarioDto;
import com.dancesys.dancesys.dto.UsuarioFilterDto;
import com.dancesys.dancesys.entity.ExperienciaAlunoModalidade;
import com.dancesys.dancesys.entity.Usuario;
import com.dancesys.dancesys.mapper.ExperienciaAlunoModalidadeMapper;
import com.dancesys.dancesys.mapper.UsuarioMapper;
import com.dancesys.dancesys.repository.ExperienciaAlunoModalidadeRepository;
import com.dancesys.dancesys.repository.UsuarioRepository;
import com.dancesys.dancesys.repository.UsuarioRepositoryCustom;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServiceImpl implements UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final UsuarioRepositoryCustom usuarioRepositoryCustom;
    private final ExperienciaAlunoModalidadeRepository  experienciaAlunoModalidadeRepository;

    public UsuarioServiceImpl(
            UsuarioRepository usuarioRepository,
            UsuarioRepositoryCustom usuarioRepositoryCustom,
            ExperienciaAlunoModalidadeRepository experienciaAlunoModalidadeRepository
    ) {
        this.usuarioRepository = usuarioRepository;
        this.usuarioRepositoryCustom = usuarioRepositoryCustom;
        this.experienciaAlunoModalidadeRepository = experienciaAlunoModalidadeRepository;
    }

    @Override
    public UsuarioDto salvar(UsuarioDto dto) throws Exception{
        Usuario usuario = new Usuario();

        try{
            if(dto.getId() == null){
                dto.setStatus(Usuario.ativo);
                dto.setCriadoEm(LocalDate.now());
                if(dto.getEnumTipo().equals(Usuario.aluno_livre)){
                    dto.setCreditos(Usuario.max_creditos);
                }else{
                    dto.setCreditos(0);
                }
            }
            usuario = usuarioRepository.save(UsuarioMapper.toEntity(dto));
            UsuarioDto newDTO = UsuarioMapper.toDto(usuario);
            return newDTO;
        }catch(Exception e){
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

    @Override
    public UsuarioAlunoDto salvarAluno(UsuarioAlunoDto dto) throws Exception{
        try{
            UsuarioDto newDto = UsuarioMapper.alunoToDto(dto);
            String mods = "/";
            for(ExperienciaAlunoModalidade ex : dto.getExpericaMod()){
                mods.concat(ex.getModalidade().toString() + "/");
            }
            newDto.setModalidades(mods);
            UsuarioDto newSave = salvar(newDto);

            UsuarioAlunoDto newAulnoDto = UsuarioMapper.toDtoAluno(newDto);
            List<ExperienciaAlunoModalidade> newModalidades = new ArrayList<>();
            for(ExperienciaAlunoModalidade ex : dto.getExpericaMod()){
                ExperienciaAlunoModalidade newEx = new ExperienciaAlunoModalidade();
                newEx.setIdAluno(UsuarioMapper.toEntity(newSave));
                newEx = experienciaAlunoModalidadeRepository.save(ex);
                newModalidades.add(newEx);
            }
            newAulnoDto.setExpericaMod(newModalidades);
            return newAulnoDto;
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Usuario> buscarUsuarios(UsuarioFilterDto filtro) throws Exception {
        return usuarioRepositoryCustom.buscasrUsuario(filtro);
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
