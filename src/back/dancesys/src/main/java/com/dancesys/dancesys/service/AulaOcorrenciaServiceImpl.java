package com.dancesys.dancesys.service;

import com.dancesys.dancesys.dto.AulaOcorrenciaDto;
import com.dancesys.dancesys.entity.Aula;
import com.dancesys.dancesys.entity.AulaOcorrencia;
import com.dancesys.dancesys.mapper.AulaMapper;
import com.dancesys.dancesys.mapper.AulaOcorrenciaMapper;
import com.dancesys.dancesys.repository.AulaOcorrenciaRepository;
import org.springframework.stereotype.Service;

@Service
public class AulaOcorrenciaServiceImpl implements AulaOcorrenciaService {
    private AulaOcorrenciaRepository aulaOcorrenciaRepository;
    private AulaServiceImpl aulaServiceImpl;

    public AulaOcorrenciaServiceImpl(
            AulaOcorrenciaRepository aulaOcorrenciaRepository,
            AulaServiceImpl aulaServiceImpl

    ) {
        this.aulaOcorrenciaRepository = aulaOcorrenciaRepository;
        this.aulaServiceImpl = aulaServiceImpl;
    }

    @Override
    public AulaOcorrenciaDto salvar(AulaOcorrenciaDto dto) throws Exception{
        AulaOcorrencia aula = new AulaOcorrencia();

        try{
            if(dto.getId() == null){
                int dia = dto.getData().getDayOfMonth();
                int mes = dto.getData().getMonthValue();
                int ano = dto.getData().getYear();

                Aula a = aulaServiceImpl.findById(dto.getIdAula().getId());
                int horainicio = a.getHorarioInicio().getHour();
                int minutoinicio = a.getHorarioInicio().getMinute();
                int horafim = a.getHorarioInicio().getHour();
                int minutofim = a.getHorarioInicio().getMinute();

                String codaula = dia + mes + ano + "." + a.getTipo() + "." + a.getIdProfessor().getId() + "." + horainicio + minutoinicio + horafim + minutofim;

                dto.setCodigoAula(codaula);

                dto.setStatus(AulaOcorrencia.ativo);
            }

            aula = aulaOcorrenciaRepository.save(AulaOcorrenciaMapper.toEntity(dto));
            AulaOcorrenciaDto newDTO = AulaOcorrenciaMapper.toDto(aula);
            return newDTO;
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public AulaOcorrenciaDto cancelar(AulaOcorrenciaDto dto) throws Exception{
        try{
            dto.setStatus(AulaOcorrencia.desativo);
            return salvar(dto);
        }catch(Exception e){
            throw new RuntimeException(e);
        }

    }
}
