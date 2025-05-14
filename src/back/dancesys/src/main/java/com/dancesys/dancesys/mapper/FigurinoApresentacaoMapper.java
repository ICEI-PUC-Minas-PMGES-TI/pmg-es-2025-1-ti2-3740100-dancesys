package com.dancesys.dancesys.mapper;

import com.dancesys.dancesys.dto.FigurinoApresentacaoDTO;
import com.dancesys.dancesys.entity.FigurinoApresentacao;

public class FigurinoApresentacaoMapper {
    public static FigurinoApresentacao toEntity(FigurinoApresentacaoDTO dto){
        if(dto == null){
            return null;
        }

        FigurinoApresentacao entity = new FigurinoApresentacao();

        entity.setId(dto.getId());
        entity.setStatus(dto.getStatus());
        entity.setTamanho(dto.getTamanho());
        entity.setCodigo(dto.getCodigo());
        entity.setIdFigurino(dto.getIdFigurino());
        entity.setIdAluno(dto.getIdAluno());
        entity.setIdApresentacaoEvento(dto.getIdApresentacaoEvento());

        return entity;
    }

    public static FigurinoApresentacaoDTO toDto(FigurinoApresentacao entity){
        if(entity == null){
            return null;
        }

        FigurinoApresentacaoDTO dto = new FigurinoApresentacaoDTO();

        dto.setId(entity.getId());
        dto.setStatus(entity.getStatus());
        dto.setTamanho(entity.getTamanho());
        dto.setCodigo(entity.getCodigo());
        dto.setIdFigurino(entity.getIdFigurino());
        dto.setIdAluno(entity.getIdAluno());
        dto.setIdApresentacaoEvento(entity.getIdApresentacaoEvento());

        return dto;
    }
}