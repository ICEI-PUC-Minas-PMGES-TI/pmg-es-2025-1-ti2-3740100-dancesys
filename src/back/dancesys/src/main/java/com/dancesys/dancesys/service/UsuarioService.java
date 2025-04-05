package com.dancesys.dancesys.service;

import com.dancesys.dancesys.dto.AlunoDTO;
import com.dancesys.dancesys.dto.LoginDTO;
import com.dancesys.dancesys.dto.UsuarioDTO;

public interface UsuarioService{
    public UsuarioDTO salvar(UsuarioDTO dto) throws Exception;

    public LoginDTO login(UsuarioDTO dto) throws Exception;

    public AlunoDTO salvarAluno(AlunoDTO dto) throws Exception;
}
