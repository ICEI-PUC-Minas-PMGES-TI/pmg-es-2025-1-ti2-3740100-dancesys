package com.dancesys.dancesys.service;

import com.dancesys.dancesys.dto.HorarioProfessorDTO;

public interface HorarioProfessorService {
    public HorarioProfessorDTO salvar(HorarioProfessorDTO dto) throws RuntimeException;
    public String excluir(Long id);
}
