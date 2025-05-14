package com.dancesys.dancesys.service;

import com.dancesys.dancesys.dto.EventoDTO;
import com.dancesys.dancesys.entity.Evento;

import java.io.IOException;
import java.util.List;

public interface EventoService {
    public EventoDTO salvar(EventoDTO dto) throws IOException;

    public List<Evento> buscar();

    public void excluir(Long idEvento) throws RuntimeException;
}
