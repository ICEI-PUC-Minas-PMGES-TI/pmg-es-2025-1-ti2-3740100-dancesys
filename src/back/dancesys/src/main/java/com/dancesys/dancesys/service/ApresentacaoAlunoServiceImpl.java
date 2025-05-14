package com.dancesys.dancesys.service;

import com.dancesys.dancesys.repository.ApresentacaoAlunoRepository;
import org.springframework.stereotype.Service;

@Service
public class ApresentacaoAlunoServiceImpl {
    private final ApresentacaoAlunoRepository apresentacaoAlunoRepository;

    public ApresentacaoAlunoServiceImpl(ApresentacaoAlunoRepository apresentacaoAlunoRepository) {
        this.apresentacaoAlunoRepository = apresentacaoAlunoRepository;
    }
}
