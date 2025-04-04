package com.dancesys.dancesys.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;


@Table(name = "Experiencia_Aluno_Modalidade")
@Entity(name = "Experiencia_Aluno_Modalidade")
@Getter
@Setter
public class ExperienciaAlunoModalidade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "modalidade", nullable = false)
    private Integer modalidade;
    public static final Integer ballet = 1;
    public static final Integer jazz = 2;
    public static final Integer contemporâneo = 3;
    public static final Integer stiletto = 4;
    public static final Integer jazz_funk = 5;
    public static final Integer sapateado = 6;
    private static final Integer street = 7;
    public static final Integer dancas_flocloricas = 8;

    @Column(name = "nivel", nullable = false)
    private Integer nivel;

    @ManyToOne
    @JoinColumn(name = "id_Aluno", nullable = false)
    private Usuario idAluno;
}

