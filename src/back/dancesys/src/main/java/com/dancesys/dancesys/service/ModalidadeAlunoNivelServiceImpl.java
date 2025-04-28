package com.dancesys.dancesys.service;

import com.dancesys.dancesys.dto.ModalidadeAlunoNivelDTO;
import com.dancesys.dancesys.entity.IdsCompostos.AlunoModalidade;
import com.dancesys.dancesys.entity.ModalidadeAlunoNivel;
import com.dancesys.dancesys.mapper.ModalidadeAlunoNivelMapper;
import com.dancesys.dancesys.repository.ModalidadeAlunoNivelRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ModalidadeAlunoNivelServiceImpl{
    private final ModalidadeAlunoNivelRepository modalidadeAlunoNivelRepository;


    public ModalidadeAlunoNivelServiceImpl(ModalidadeAlunoNivelRepository modalidadeAlunoNivelRepository) {
        this.modalidadeAlunoNivelRepository = modalidadeAlunoNivelRepository;
    }

    public ModalidadeAlunoNivel salvar(ModalidadeAlunoNivelDTO dto) {
        AlunoModalidade id = new AlunoModalidade(dto.getIdAluno(), dto.getIdModalidade());
        ModalidadeAlunoNivel modalidadeAlunoNivel = ModalidadeAlunoNivelMapper.toEntity(dto);

        modalidadeAlunoNivel.setId(id);
        return modalidadeAlunoNivelRepository.save(modalidadeAlunoNivel);
    }

    public void excluirAll(List<ModalidadeAlunoNivelDTO> list, Long idAluno){
        List<Long> ids = new ArrayList<>();
        for(ModalidadeAlunoNivelDTO dto : list){
            ids.add(dto.getIdModalidade());
        }
        List<ModalidadeAlunoNivel> newList = modalidadeAlunoNivelRepository.findByIdAlunoIdAndIdModalidadeIdNotIn(idAluno, ids);
        modalidadeAlunoNivelRepository.deleteAll(newList);
    }

    public void excluirAllPorAluno(Long idAluno){
        List<ModalidadeAlunoNivel> mods = modalidadeAlunoNivelRepository.findByIdAlunoId(idAluno);
        modalidadeAlunoNivelRepository.deleteAll(mods);
    }
}
