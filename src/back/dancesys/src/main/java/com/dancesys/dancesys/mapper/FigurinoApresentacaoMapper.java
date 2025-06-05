package com.dancesys.dancesys.mapper;

import com.dancesys.dancesys.dto.FigurinoApresentacaoDTO;
import com.dancesys.dancesys.entity.Aluno;
import com.dancesys.dancesys.entity.ApresentacaoEvento;
import com.dancesys.dancesys.entity.Figurino;
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

        Figurino figurino = new Figurino();
        figurino.setId(dto.getIdFigurino());
        entity.setIdFigurino(figurino);

        Aluno aluno = new Aluno();
        aluno.setId(dto.getIdAluno());
        entity.setIdAluno(aluno);

        ApresentacaoEvento ae = new ApresentacaoEvento();
        ae.setId(dto.getIdApresentacaoEvento());
        entity.setIdApresentacaoEvento(ae);

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
        dto.setIdFigurino(entity.getIdFigurino().getId());
        dto.setIdAluno(entity.getIdAluno().getId());
        dto.setIdApresentacaoEvento(entity.getIdApresentacaoEvento().getId());

        return dto;
    }
}