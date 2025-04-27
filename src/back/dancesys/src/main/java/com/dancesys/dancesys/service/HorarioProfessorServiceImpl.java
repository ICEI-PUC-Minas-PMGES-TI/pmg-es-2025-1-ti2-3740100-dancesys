package com.dancesys.dancesys.service;

import com.dancesys.dancesys.dto.HorarioProfessorDTO;
import com.dancesys.dancesys.entity.HorarioProfessor;
import com.dancesys.dancesys.mapper.HorarioProfessorMapper;
import com.dancesys.dancesys.repository.HorarioProfessorRepository;
import org.springframework.stereotype.Service;

@Service
public class HorarioProfessorServiceImpl implements HorarioProfessorService {
    private final HorarioProfessorRepository horarioProfessorRepository;

    public HorarioProfessorServiceImpl(HorarioProfessorRepository horarioProfessorRepository) {
        this.horarioProfessorRepository = horarioProfessorRepository;
    }

    @Override
    public HorarioProfessorDTO salvar(HorarioProfessorDTO dto) throws RuntimeException {
        HorarioProfessor entity = new HorarioProfessor();
        try{
            entity = horarioProfessorRepository.save(HorarioProfessorMapper.toEntity(dto));
            return HorarioProfessorMapper.toDto(entity);
        }catch(Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public String excluir(Long id){
        horarioProfessorRepository.deleteById(id);
        return "Horario excluido com sucesso!";
    }
}
