package com.dancesys.dancesys.service;

import com.dancesys.dancesys.dto.*;
import com.dancesys.dancesys.entity.*;
import com.dancesys.dancesys.infra.PaginatedResponse;
import com.dancesys.dancesys.mapper.*;
import com.dancesys.dancesys.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
    private final FilesServiceImpl filesServiceImpl;

    public UsuarioServiceImpl(
            UsuarioRepository usuarioRepository,
            AlunoServiceImpl alunoServiceImpl,
            ModalidadeAlunoNivelServiceImpl modalidadeAlunoNivelServiceImpl,
            ProfessorServiceImpl professorServiceImpl,
            ProfessorModalidadeServiceImpl professorModalidadeServiceImpl,
            EmailServiceImpl emailServiceImpl,
            DividendoServiceImpl dividendoServiceImpl,
            FilesServiceImpl filesServiceImpl
    ) {
        this.usuarioRepository = usuarioRepository;
        this.alunoServiceImpl = alunoServiceImpl;
        this.modalidadeAlunoNivelServiceImpl = modalidadeAlunoNivelServiceImpl;
        this.professorServiceImpl = professorServiceImpl;
        this.professorModalidadeServiceImpl = professorModalidadeServiceImpl;
        this.emailServiceImpl = emailServiceImpl;
        this.dividendoServiceImpl = dividendoServiceImpl;
        this.filesServiceImpl = filesServiceImpl;
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
    public void trocarFoto(UsuarioDTO dto) throws Exception{
        try{
            if(dto.getBase64() != null && !dto.getBase64().equals("")){
                MultipartFile foto = filesServiceImpl.convertBase64ToMultipartFile(dto.getBase64(), dto.getNomeArquivo());
                String newUrl = filesServiceImpl.uploadFile(foto);
                if(dto.getUrlFoto() != null && !dto.getUrlFoto().equals("")){
                    filesServiceImpl.deleteFileByUrl(dto.getUrlFoto());
                }
                dto.setUrlFoto(newUrl);
            }
            usuarioRepository.save(UsuarioMapper.toEntity(dto));
        }catch (RuntimeException e){
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void trocarSenha(UsuarioDTO dto) throws Exception{
        try{
            usuarioRepository.save(UsuarioMapper.toEntity(dto));
        }catch (RuntimeException e){
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public UsuarioDTO findById(Long id) throws Exception{
        try{
            Usuario usuario = usuarioRepository.findById(id);
            return UsuarioMapper.toDTO(usuario);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public LoginCookie login(LoginDTO dto) throws RuntimeException{
        try{
            Usuario user = usuarioRepository.findByEmailAndSenha(dto.getEmail(), dto.getSenha());
            if(user==null){
                throw new RuntimeException("Usuario não encontrado");
            }

            if(user.getStatus().equals(Usuario.desativo)){
                throw new Exception("Usuario desativado");
            }

            return UsuarioMapper.toLoginDTO(user);
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
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
    public PaginatedResponse<Aluno> buscarAlunos(AlunoFilter filtro) {
        return alunoServiceImpl.buscar(filtro);
    }

    @Override
    public PaginatedResponse<Professor> buscarProfessores(ProfessorFilter filtro){
        return professorServiceImpl.buscar(filtro);
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

    @Override
    public Object validacaoLogin(LoginCookie cookie) throws RuntimeException{
        if(cookie.getTipo().equals(Usuario.aluno)){
            return alunoServiceImpl.findByIdUsuario(cookie.getId());
        }else if(cookie.getTipo().equals(Usuario.funcionario)){
            return professorServiceImpl.findByIdUsuario(cookie.getId());
        }else{
            return usuarioRepository.findById(cookie.getId());
        }
    }
}
