package com.dancesys.dancesys.service;

import com.dancesys.dancesys.dto.*;
import com.dancesys.dancesys.entity.*;
import com.dancesys.dancesys.mapper.*;
import com.dancesys.dancesys.repository.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class UsuarioServiceImpl implements UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final AlunoServiceImpl alunoServiceImpl;
    private final ModalidadeAlunoNivelServiceImpl modalidadeAlunoNivelServiceImpl;
    private final ProfessorServiceImpl professorServiceImpl;
    private final ProfessorModalidadeServiceImpl professorModalidadeServiceImpl;
    private final AlunoRepository alunoRepository;
    private final EmailServiceImpl emailServiceImpl;

    public UsuarioServiceImpl(
            UsuarioRepository usuarioRepository,
            AlunoServiceImpl alunoServiceImpl,
            ModalidadeAlunoNivelServiceImpl modalidadeAlunoNivelServiceImpl,
            ProfessorServiceImpl professorServiceImpl,
            ProfessorModalidadeServiceImpl professorModalidadeServiceImpl,
            AlunoRepository alunoRepository,
            EmailServiceImpl emailServiceImpl
            ) {
        this.usuarioRepository = usuarioRepository;
        this.alunoServiceImpl = alunoServiceImpl;
        this.modalidadeAlunoNivelServiceImpl = modalidadeAlunoNivelServiceImpl;
        this.professorServiceImpl = professorServiceImpl;
        this.professorModalidadeServiceImpl = professorModalidadeServiceImpl;
        this.alunoRepository = alunoRepository;
        this.emailServiceImpl = emailServiceImpl;
    }

    @Override
    public UsuarioDTO salvar(UsuarioDTO dto) throws Exception{
        Usuario usuario = new Usuario();
        try {
            if(dto.getId()==null){
                dto.setStatus(Usuario.ativo);
                dto.setCriadoEm(LocalDate.now());
                dto.setSenha(Usuario.SENHA_PADRAO);
                emailServiceImpl.enviarEmailHtml(dto.getEmail(), dto.getSenha());
            }
            usuario = usuarioRepository.save(UsuarioMapper.toEntity(dto));
            return UsuarioMapper.toDTO(usuario);
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

            if(dto.getId() != null){
                modalidadeAlunoNivelServiceImpl.excluirAll(dto.getModalidades(), newAluno.getId());
            }

            for (ModalidadeAlunoNivelDTO obj : dto.getModalidades()) {
                obj.setIdAluno(newAluno.getId());

                ModalidadeAlunoNivel mod = modalidadeAlunoNivelServiceImpl.salvar(obj);

                modList.add(ModalidadeAlunoNivelMapper.toDto(mod));
            }

            AlunoDTO newDto = AlunoMapper.allToDTO(user, newAluno, modList);
            return newDto;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public ProfessorDTO salvarProfessor(ProfessorDTO dto) throws Exception{
        try{
            UsuarioDTO user = salvar(UsuarioMapper.professorDTOtoDto(dto));
            Professor professor = ProfessorMapper.toEntity(dto);
            professor.setIdUsuario(UsuarioMapper.toEntity(user));
            Professor newProfessor = professorServiceImpl.salvar(professor);
            List<ProfessorModalidadeDTO> modList = new ArrayList<>();
            for(ProfessorModalidadeDTO obj : dto.getModalidades()){
                obj.setIdProfessor(newProfessor.getId());

                ProfessorModalidade mod = professorModalidadeServiceImpl.salvar(obj);

                modList.add(ProfessorModalidadeMapper.toDto(mod));
            }

            return ProfessorMapper.AlltoDto(user, newProfessor, modList);
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public List<Aluno> buscarAlunos(String nome, String cpf, String email, Integer tipo, Integer status) {
        return alunoRepository.buscarAlunos(nome, cpf, email, tipo, status);
    }

    @Override
    public List<Usuario> buscar(){
        return usuarioRepository.findAll();
    }

    @Override
    public Usuario alterarStatus(Long id){
        Usuario u = usuarioRepository.findById(id);
        if(u.getStatus().equals(Usuario.desativo)){
            u.setStatus(Usuario.ativo);
        }else{
            u.setStatus(Usuario.desativo);
        }
        usuarioRepository.save(u);
        return u;
    }
}
