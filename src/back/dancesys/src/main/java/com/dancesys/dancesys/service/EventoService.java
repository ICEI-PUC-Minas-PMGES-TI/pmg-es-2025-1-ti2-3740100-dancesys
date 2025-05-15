package com.dancesys.dancesys.service;

import com.dancesys.dancesys.dto.EventoDTO;
import com.dancesys.dancesys.dto.EventoFilter;
import com.dancesys.dancesys.entity.Evento;
import com.dancesys.dancesys.infra.PaginatedResponse;

import java.io.IOException;
import java.util.List;

public interface EventoService {
    public EventoDTO salvar(EventoDTO dto) throws IOException;

    public PaginatedResponse<Evento> buscar(EventoFilter filtro);

    public void excluir(Long idEvento) throws RuntimeException;
}
