package com.dancesys.dancesys.service;

import com.dancesys.dancesys.dto.EventoDTO;

import java.io.IOException;

public interface EventoService {
    public EventoDTO salvar(EventoDTO dto) throws IOException;
}
