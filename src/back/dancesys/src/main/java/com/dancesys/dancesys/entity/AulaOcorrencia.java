package com.dancesys.dancesys.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;


@Table(name = "Aula_Ocorrencia")
@Entity(name = "Aula_Ocorrencia")
@Getter
@Setter
public class AulaOcorrencia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "codigo_aula", nullable = false)
    private String codigoAula;

    @Column(name = "lista_ids_alunos", nullable = true)
    private String listaIdsAlunos;

    @Column(name = "data", nullable = false)
    private LocalDate data;

    @Column(name = "status", nullable = false)
    private Integer status;
    public static final Integer ativo = 1;
    public static final Integer inativo = 0;

    @Column(name = "motivo_cancelamento", nullable = true)
    private String motivoCancelamento;

    @ManyToOne
    @JoinColumn(name = "id_Aula", nullable = false)
    private Aula idAula;

}

