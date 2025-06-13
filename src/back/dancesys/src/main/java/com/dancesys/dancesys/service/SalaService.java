package com.dancesys.dancesys.service;

import com.dancesys.dancesys.dto.SalaDTO;
import com.dancesys.dancesys.entity.Sala;


import java.util.List;

public interface SalaService {
    public SalaDTO salvar(SalaDTO dto) throws Exception;

    public List<Sala> buscar();

    public void excluir(Long id);
}
