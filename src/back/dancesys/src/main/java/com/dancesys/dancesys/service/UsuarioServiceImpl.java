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
    private final ModalidadeAlunoNivelRepository modalidadeAlunoNivelRepository;

    public UsuarioServiceImpl(
            UsuarioRepository usuarioRepository,
            AlunoServiceImpl alunoServiceImpl,
            ModalidadeAlunoNivelServiceImpl modalidadeAlunoNivelServiceImpl,
            ProfessorServiceImpl professorServiceImpl,
            ProfessorModalidadeServiceImpl professorModalidadeServiceImpl,
            AlunoRepository alunoRepository,
            ModalidadeAlunoNivelRepository modalidadeAlunoNivelRepository
    ) {
        this.usuarioRepository = usuarioRepository;
        this.alunoServiceImpl = alunoServiceImpl;
        this.modalidadeAlunoNivelServiceImpl = modalidadeAlunoNivelServiceImpl;
        this.professorServiceImpl = professorServiceImpl;
        this.professorModalidadeServiceImpl = professorModalidadeServiceImpl;
        this.alunoRepository = alunoRepository;
        this.modalidadeAlunoNivelRepository = modalidadeAlunoNivelRepository;
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
            for (ModalidadeAlunoNivelDTO obj : dto.getModalidades()) {
                obj.setAluno(newAluno);
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
                obj.setProfessor(newProfessor);
                obj.setIdProfessor(newProfessor.getId());

                ProfessorModalidade mod = professorModalidadeServiceImpl.salvar(obj);

                modList.add(ProfessorModalidadeMapper.toDto(mod));
            }

            ProfessorDTO newDto = ProfessorMapper.AlltoDto(user, newProfessor, modList);
            return newDto;
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public List<AlunoDTO> buscarAlunos(String nome, String cpf, String email, Integer tipo, Integer status) {
        List<Aluno> alunos = alunoRepository.buscarAlunos(nome, cpf, email, tipo, status);
        List<AlunoDTO> dtos = new ArrayList<>();
        for(Aluno aluno : alunos){
            List<ModalidadeAlunoNivel> modEntity = modalidadeAlunoNivelRepository.findByIdAluno_Id(aluno.getId());
            List<ModalidadeAlunoNivelDTO> modList = new ArrayList<>();
            for(ModalidadeAlunoNivel obj : modEntity){
                modList.add(ModalidadeAlunoNivelMapper.toDto(obj));
            }

            AlunoDTO dto = AlunoMapper.toDto(aluno);
            dto.setModalidades(modList);
            dtos.add(dto);
        }

        return dtos;
    }

    @Override
    public List<Usuario> buscar(){
        return usuarioRepository.findAll();
    }
}
