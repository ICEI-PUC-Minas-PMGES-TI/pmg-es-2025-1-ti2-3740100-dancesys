package com.dancesys.dancesys.service;

import com.dancesys.dancesys.dto.EventoDTO;
import com.dancesys.dancesys.entity.Evento;
import com.dancesys.dancesys.mapper.EventoMapper;
import com.dancesys.dancesys.repository.EventoRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class EventoServiceImpl implements EventoService {
    private final EventoRepository eventoRepository;
    private final FilesServiceImpl filesServiceImpl;

    public EventoServiceImpl(
            EventoRepository eventoRepository,
            FilesServiceImpl filesServiceImpl
    ) {
        this.eventoRepository = eventoRepository;
        this.filesServiceImpl = filesServiceImpl;
    }

    @Override
    public EventoDTO salvar(EventoDTO dto) throws IOException {
        try{
            return EventoMapper.toDto(eventoRepository.save(EventoMapper.toEntity(dto)));
        }catch(Exception e){
            throw new IOException(e.getMessage());
        }
    }
}
