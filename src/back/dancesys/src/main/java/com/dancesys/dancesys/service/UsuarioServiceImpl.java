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
    private final EmailServiceImpl emailServiceImpl;
    private final DividendoServiceImpl dividendoServiceImpl;

    public UsuarioServiceImpl(
            UsuarioRepository usuarioRepository,
            AlunoServiceImpl alunoServiceImpl,
            ModalidadeAlunoNivelServiceImpl modalidadeAlunoNivelServiceImpl,
            ProfessorServiceImpl professorServiceImpl,
            ProfessorModalidadeServiceImpl professorModalidadeServiceImpl,
            EmailServiceImpl emailServiceImpl,
            DividendoServiceImpl dividendoServiceImpl
    ) {
        this.usuarioRepository = usuarioRepository;
        this.alunoServiceImpl = alunoServiceImpl;
        this.modalidadeAlunoNivelServiceImpl = modalidadeAlunoNivelServiceImpl;
        this.professorServiceImpl = professorServiceImpl;
        this.professorModalidadeServiceImpl = professorModalidadeServiceImpl;
        this.emailServiceImpl = emailServiceImpl;
        this.dividendoServiceImpl = dividendoServiceImpl;
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

            if(dto.getId()==null){
                dividendoServiceImpl.gerarMatricula(newAluno);
                dividendoServiceImpl.gerarMensalidade(newAluno);
            }

            List<ModalidadeAlunoNivelDTO> modList = new ArrayList<>();

            if(dto.getId() != null){
                if(dto.getModalidades().isEmpty()){
                    modalidadeAlunoNivelServiceImpl.excluirAllPorAluno(dto.getId());
                }else{
                    modalidadeAlunoNivelServiceImpl.excluirAll(dto.getModalidades(), dto.getId());
                }
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
            UsuarioDTO user = salvar(UsuarioMapper.toDTO(dto.getUsuario()));
            Professor professor = ProfessorMapper.toEntity(dto);
            professor.setIdUsuario(UsuarioMapper.toEntity(user));
            Professor newProfessor = professorServiceImpl.salvar(professor);
            if(dto.getId() != null){
                if(dto.getModalidades().isEmpty()){
                    professorModalidadeServiceImpl.excluirAllPorIdProfessor(dto.getId());
                }else{
                    professorModalidadeServiceImpl.excluirAll(dto.getId(), dto.getModalidades());
                }
            }
            for(Long obj : dto.getModalidades()){
                ProfessorModalidade pm = new ProfessorModalidade();
                pm.setIdProfessor(professor);
                Modalidade mod = new Modalidade();
                mod.setId(obj);
                pm.setIdModalidade(mod);

                professorModalidadeServiceImpl.salvar(pm);

            }

            return ProfessorMapper.AlltoDto(user, newProfessor, dto.getModalidades());
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public List<Aluno> buscarAlunos(String nome, String cpf, String email, Integer tipo, Integer status) {
        return alunoServiceImpl.buscarAlunos(nome, cpf, email, tipo, status);
    }

    @Override
    public List<Professor> buscarProfessores(String nome, String cpf, String email, Integer status){
        return professorServiceImpl.buscarProfessores(nome, cpf, email, status);
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

    @Override
    public Long acharIdAlunoUsuario(Long id){
        Aluno aluno = alunoServiceImpl.findByIdUsuario(id);
        return aluno.getId();
    }

    @Override
    public void gerarBoletosMensalJob() throws Exception{
        alunoServiceImpl.gerarBoletosMenslaidadeJob();
    }
}
