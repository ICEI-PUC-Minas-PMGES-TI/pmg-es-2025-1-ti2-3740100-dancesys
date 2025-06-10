package com.dancesys.dancesys.service;

import com.dancesys.dancesys.dto.AulaDTO;
import com.dancesys.dancesys.dto.AulaFilter;
import com.dancesys.dancesys.dto.AulaOcorrenciaFilter;
import com.dancesys.dancesys.dto.MensagemDTO;
import com.dancesys.dancesys.entity.Aula;
import com.dancesys.dancesys.entity.AulaOcorrencia;
import com.dancesys.dancesys.infra.PaginatedResponse;

import java.util.List;

public interface AulaService {
    public AulaDTO salvar(AulaDTO dto) throws Exception;

    public void mudarStatus(Long id) throws Exception;

    public void gerarAulasJobMensal() throws Exception;

    public PaginatedResponse<Aula> buscar(AulaFilter filter);

    public PaginatedResponse<AulaOcorrencia> buscar(AulaOcorrenciaFilter filtro);

    public void cancelar(Long id, MensagemDTO mensagem) throws RuntimeException;

    public List<AulaOcorrencia> buscarAo2(AulaOcorrenciaFilter filtro);
}
