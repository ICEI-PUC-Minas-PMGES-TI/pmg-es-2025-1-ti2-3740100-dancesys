package com.dancesys.dancesys.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;


@Table(name = "Chamada_Aula")
@Entity(name = "Chamada_Aula")
@Getter
@Setter
public class ChamadaAula {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "presenca", nullable = false)
    private Integer presenca;

    @ManyToOne
    @JoinColumn(name = "id_Aula_Ocorrencia", nullable = false)
    private AulaOcorrencia idAulaOcorrencia;
    public static final Integer presente =1;
    public static final Integer faltante =2;

    @ManyToOne
    @JoinColumn(name = "id_Aluno", nullable = false)
    private Usuario idAluno;
}

