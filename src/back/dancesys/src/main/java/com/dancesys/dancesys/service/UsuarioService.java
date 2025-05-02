package com.dancesys.dancesys.service;

import com.dancesys.dancesys.dto.*;
import com.dancesys.dancesys.entity.Aluno;
import com.dancesys.dancesys.entity.Professor;
import com.dancesys.dancesys.entity.Usuario;
import com.dancesys.dancesys.infra.PaginatedResponse;

import java.util.List;

public interface UsuarioService{
    public UsuarioDTO salvar(UsuarioDTO dto) throws Exception;

    public LoginDTO login(UsuarioDTO dto) throws Exception;

    public AlunoDTO salvarAluno(AlunoDTO dto) throws Exception;

    public ProfessorDTO salvarProfessor(ProfessorDTO dto) throws Exception;

    public List<Usuario> buscar();

    public Usuario alterarStatus(Long id);

    public PaginatedResponse<Aluno> buscarAlunos(AlunoFilter filtro);

    public PaginatedResponse<Professor> buscarProfessor(ProfessorFilter filtro);
}
