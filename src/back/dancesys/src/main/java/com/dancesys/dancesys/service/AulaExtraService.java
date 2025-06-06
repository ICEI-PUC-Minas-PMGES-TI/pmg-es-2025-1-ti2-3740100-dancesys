package com.dancesys.dancesys.service;

import com.dancesys.dancesys.dto.AulaExtraDTO;
import com.dancesys.dancesys.dto.AulaExtraFilter;
import com.dancesys.dancesys.dto.MensagemDTO;
import com.dancesys.dancesys.entity.AulaExtra;
import com.dancesys.dancesys.infra.PaginatedResponse;

public interface AulaExtraService {
    AulaExtraDTO salvar(AulaExtraDTO dto) throws RuntimeException;

    public PaginatedResponse<AulaExtra> buscar(AulaExtraFilter filtro);

    public void aceitar(Long id, Long idSala) throws RuntimeException;

    public void indeferir(Long id, MensagemDTO msg) throws RuntimeException;
}