package com.dancesys.dancesys.service;

import com.dancesys.dancesys.dto.AulaDto;
import com.dancesys.dancesys.entity.Aula;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface AulaService {

    public AulaDto salvar(AulaDto dto) throws Exception;

    public List<Aula> findAllAulas();

    public ResponseEntity<Aula> desativar(Long id) throws Exception;

    public Aula findById(Long id);
}
