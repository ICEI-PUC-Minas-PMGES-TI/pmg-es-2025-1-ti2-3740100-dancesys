package com.dancesys.dancesys.service;

import com.dancesys.dancesys.dto.AlunoDTO;
import com.dancesys.dancesys.dto.LoginDTO;
import com.dancesys.dancesys.dto.ModalidadeAlunoNivelDTO;
import com.dancesys.dancesys.dto.UsuarioDTO;
import com.dancesys.dancesys.entity.Aluno;
import com.dancesys.dancesys.entity.IdsCompostos.AlunoModalidade;
import com.dancesys.dancesys.entity.Modalidade;
import com.dancesys.dancesys.entity.ModalidadeAlunoNivel;
import com.dancesys.dancesys.entity.Usuario;
import com.dancesys.dancesys.mapper.AlunoMapper;
import com.dancesys.dancesys.mapper.ModalidadeAlunoNivelMapper;
import com.dancesys.dancesys.mapper.UsuarioMapper;
import com.dancesys.dancesys.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class UsuarioServiceImpl implements UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final AlunoServiceImpl alunoServiceImpl;
    private final ModalidadeAlunoNivelServiceImpl modalidadeAlunoNivelServiceImpl;

    public UsuarioServiceImpl(
            UsuarioRepository usuarioRepository,
            AlunoServiceImpl alunoServiceImpl,
            ModalidadeAlunoNivelServiceImpl modalidadeAlunoNivelServiceImpl
    ) {
        this.usuarioRepository = usuarioRepository;
        this.alunoServiceImpl = alunoServiceImpl;
        this.modalidadeAlunoNivelServiceImpl = modalidadeAlunoNivelServiceImpl;
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

    @Override
    public AlunoDTO salvarAluno(AlunoDTO dto) throws Exception{
        try{
            UsuarioDTO user = salvar(UsuarioMapper.alunoDTOtoDto(dto));
            Aluno aluno = AlunoMapper.toEntity(dto);
            aluno.setIdUsuario(UsuarioMapper.toEntity(user));
            Aluno newAluno = alunoServiceImpl.salvar(aluno);
            List<ModalidadeAlunoNivelDTO> modList = new ArrayList<>();
            for (ModalidadeAlunoNivelDTO obj : dto.getModalidades()) {
                obj.setAluno(newAluno);
                obj.setIdAluno(newAluno.getId());

                ModalidadeAlunoNivel mod = modalidadeAlunoNivelServiceImpl.salvar(obj);

                modList.add(ModalidadeAlunoNivelMapper.toDto(mod));
            }

            AlunoDTO newDto = AlunoMapper.allToDTO(user, newAluno, modList);
            return newDto;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Usuario> buscar(){
        return usuarioRepository.findAll();
    }
}
