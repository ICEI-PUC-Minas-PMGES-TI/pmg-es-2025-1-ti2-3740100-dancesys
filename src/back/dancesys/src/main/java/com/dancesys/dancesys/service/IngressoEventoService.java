package com.dancesys.dancesys.service;

import com.dancesys.dancesys.dto.IngressoEventoDTO;

import java.util.List;

public interface IngressoEventoService {
    public IngressoEventoDTO salvar(IngressoEventoDTO dto) throws Exception;

    public List<IngressoEventoDTO> buscar();

    public void deletar (Long id);
}
