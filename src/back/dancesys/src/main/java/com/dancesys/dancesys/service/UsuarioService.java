package com.dancesys.dancesys.service;

import com.dancesys.dancesys.dto.*;
import com.dancesys.dancesys.entity.Aluno;
import com.dancesys.dancesys.entity.Professor;
import com.dancesys.dancesys.entity.Usuario;

import java.util.List;

public interface UsuarioService{
    public UsuarioDTO salvar(UsuarioDTO dto) throws Exception;

    public LoginDTO login(UsuarioDTO dto) throws Exception;

    public AlunoDTO salvarAluno(AlunoDTO dto) throws Exception;

    public ProfessorDTO salvarProfessor(ProfessorDTO dto) throws Exception;

    public List<Aluno> buscarAlunos(String nome, String cpf, String email, Integer tipo, Integer status);

    public List<Usuario> buscar();

    public Usuario alterarStatus(Long id);

    public List<Professor> buscarProfessores(String nome, String cpf, String email, Integer status);

    public Long acharIdAlunoUsuario(Long id);
}
