package com.dancesys.dancesys.service;

import com.dancesys.dancesys.dto.HorarioProfessorDTO;
import com.dancesys.dancesys.dto.HorarioProfessorFilter;
import com.dancesys.dancesys.entity.HorarioProfessor;

import java.util.List;

public interface HorarioProfessorService {
    public HorarioProfessorDTO salvar(HorarioProfessorDTO dto) throws RuntimeException;
    public String excluir(Long id);
    public List<HorarioProfessor> buscar(HorarioProfessorFilter filtro);
}
