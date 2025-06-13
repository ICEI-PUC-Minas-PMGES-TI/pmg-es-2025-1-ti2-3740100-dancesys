package com.dancesys.dancesys.service;

import com.dancesys.dancesys.dto.ModalidadeDTO;
import com.dancesys.dancesys.entity.Modalidade;
import com.dancesys.dancesys.mapper.ModalideMapper;
import com.dancesys.dancesys.repository.ModalidadeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ModalidadeServiceImpl implements ModalidadeService{
    private final ModalidadeRepository modalidadeRepository;

    public ModalidadeServiceImpl(ModalidadeRepository modalidadeRepository) {
        this.modalidadeRepository = modalidadeRepository;
    }

    @Override
    public ModalidadeDTO salvar(ModalidadeDTO dto) throws Exception{
        Modalidade mod = new Modalidade();
        try{
            mod = modalidadeRepository.save(ModalideMapper.toEntity(dto));
            ModalidadeDTO newDto = ModalideMapper.toDto(mod);
            return newDto;
        }catch(Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public List<Modalidade> buscar(){
        return modalidadeRepository.findAll();
    }

    @Override
    public void excluir(Long id){
        modalidadeRepository.deleteById(id);
    }
}
