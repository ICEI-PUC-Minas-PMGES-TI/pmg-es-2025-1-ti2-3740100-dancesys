package com.dancesys.dancesys.service;

import com.dancesys.dancesys.entity.ModalidadeAlunoNivel;

import java.util.List;

public interface ModalidadeAlunoNivelService {
    public List<ModalidadeAlunoNivel> buscarTodos();

    public String excluir(Long idAluno, Long idModalidade) throws Exception;
}
