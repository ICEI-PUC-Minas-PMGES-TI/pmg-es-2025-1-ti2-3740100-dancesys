package com.dancesys.dancesys.service;

import com.dancesys.dancesys.dto.ModalidadeAlunoNivelDTO;
import com.dancesys.dancesys.entity.Aluno;
import com.dancesys.dancesys.entity.IdsCompostos.AlunoModalidade;
import com.dancesys.dancesys.entity.Modalidade;
import com.dancesys.dancesys.entity.ModalidadeAlunoNivel;
import com.dancesys.dancesys.mapper.ModalidadeAlunoNivelMapper;
import com.dancesys.dancesys.repository.ModalidadeAlunoNivelRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ModalidadeAlunoNivelServiceImpl implements ModalidadeAlunoNivelService{
    private final ModalidadeAlunoNivelRepository modalidadeAlunoNivelRepository;


    public ModalidadeAlunoNivelServiceImpl(ModalidadeAlunoNivelRepository modalidadeAlunoNivelRepository) {
        this.modalidadeAlunoNivelRepository = modalidadeAlunoNivelRepository;
    }

    public ModalidadeAlunoNivel salvar(ModalidadeAlunoNivelDTO dto) {
        AlunoModalidade id = new AlunoModalidade(dto.getIdAluno(), dto.getIdModalidade());
        ModalidadeAlunoNivel modalidadeAlunoNivel = new ModalidadeAlunoNivel();
        Modalidade newModalidade = new Modalidade();
        newModalidade.setId(dto.getIdModalidade());
        modalidadeAlunoNivel.setId(id);
        modalidadeAlunoNivel.setNivel(dto.getNivel());
        modalidadeAlunoNivel.setIdAluno(dto.getAluno());
        modalidadeAlunoNivel.setIdModalidade(dto.getModalidade());
        return modalidadeAlunoNivelRepository.save(modalidadeAlunoNivel);
    }

    @Override
    public List<ModalidadeAlunoNivel> buscarTodos(){
        return modalidadeAlunoNivelRepository.findAll();
    }
}
