package com.dancesys.dancesys.service;

import com.dancesys.dancesys.dto.HorarioProfessorDTO;
import com.dancesys.dancesys.dto.HorarioProfessorFilter;
import com.dancesys.dancesys.entity.HorarioProfessor;
import com.dancesys.dancesys.mapper.HorarioProfessorMapper;
import com.dancesys.dancesys.repository.HorarioProfessorRepository;
import com.dancesys.dancesys.repository.HorarioProfessorRepositoryCustom;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HorarioProfessorServiceImpl implements HorarioProfessorService {
    private final HorarioProfessorRepository horarioProfessorRepository;
    private final HorarioProfessorRepositoryCustom  horarioProfessorRepositoryCustom;

    public HorarioProfessorServiceImpl(
            HorarioProfessorRepository horarioProfessorRepository,
            HorarioProfessorRepositoryCustom horarioProfessorRepositoryCustom
    ) {
        this.horarioProfessorRepository = horarioProfessorRepository;
        this.horarioProfessorRepositoryCustom = horarioProfessorRepositoryCustom;
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

    @Override
    public List<HorarioProfessor> buscar(HorarioProfessorFilter filtro){
        return horarioProfessorRepositoryCustom.buscar(filtro);
    }
}
