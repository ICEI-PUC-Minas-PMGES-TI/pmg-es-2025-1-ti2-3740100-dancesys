package com.dancesys.dancesys.service;

import com.dancesys.dancesys.dto.EventoDTO;
import com.dancesys.dancesys.entity.Evento;
import com.dancesys.dancesys.mapper.EventoMapper;
import com.dancesys.dancesys.repository.EventoRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

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
            if(dto.getImgBase64() != null && !dto.getImgBase64().equals("")){
                MultipartFile foto = filesServiceImpl.convertBase64ToMultipartFile(dto.getImgBase64(), dto.getNomeArquivo());
                String newUrl = filesServiceImpl.uploadFile(foto);
                if(dto.getUrlFoto() != null && !dto.getUrlFoto().equals("")){
                    filesServiceImpl.deleteFileByUrl(dto.getUrlFoto());
                }
                dto.setUrlFoto(newUrl);
            }
            return EventoMapper.toDto(eventoRepository.save(EventoMapper.toEntity(dto)));
        }catch(Exception e){
            throw new IOException(e.getMessage());
        }
    }

    @Override
    public List<Evento> buscar(){
        return eventoRepository.findAll();
    }
}
