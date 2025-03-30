package com.dancesys.dancesys.service;

import com.dancesys.dancesys.dto.LoginDto;
import com.dancesys.dancesys.dto.UsuarioDto;
import com.dancesys.dancesys.entity.Usuario;

public interface UsuarioService {
    public UsuarioDto salvar(UsuarioDto dto) throws Exception;

    public LoginDto login(UsuarioDto dto) throws Exception;

    public Usuario mudarStatus(Long id) throws Exception;
}
