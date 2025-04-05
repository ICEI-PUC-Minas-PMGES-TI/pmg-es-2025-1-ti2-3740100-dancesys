package com.dancesys.dancesys.service;


import com.dancesys.dancesys.dto.ModalidadeDTO;
import com.dancesys.dancesys.entity.Modalidade;

import java.util.List;

public interface ModalidadeService {
    public ModalidadeDTO salvar(ModalidadeDTO dto) throws Exception;

    public List<Modalidade> buscar();

    public String excluir(Long id);
}
