package com.dancesys.dancesys.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class FigurinoDTO {
    private Long id;
    private String nome;
    private Integer tipo;
    private BigDecimal valor;
    private String base64;
    private String nomeArquivo;
    private String urlFoto;
}