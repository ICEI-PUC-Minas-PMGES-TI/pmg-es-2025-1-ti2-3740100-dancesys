package com.dancesys.dancesys.service;

import com.dancesys.dancesys.dto.HorarioProfessorDTO;
import com.dancesys.dancesys.dto.HorarioProfessorFilter;
import com.dancesys.dancesys.entity.HorarioProfessor;
import com.dancesys.dancesys.infra.PaginatedResponse;

import java.util.List;

public interface HorarioProfessorService {
    public HorarioProfessorDTO salvar(HorarioProfessorDTO dto) throws RuntimeException;
    public String excluir(Long id);
    public PaginatedResponse<HorarioProfessor> buscar(HorarioProfessorFilter filtro);
}
