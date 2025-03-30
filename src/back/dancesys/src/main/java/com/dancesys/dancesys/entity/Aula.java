package com.dancesys.dancesys.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;


@Table(name = "Aula")
@Entity(name = "Aula")
@Getter
@Setter
public class Aula {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "tipo", nullable = false)
    private Integer tipo;
    public static final Integer ballet = 1;
    public static final Integer jazz = 2;
    public static final Integer contemporâneo = 3;
    public static final Integer stiletto = 4;
    public static final Integer jazz_funk = 5;
    public static final Integer sapateado = 6;
    private static final Integer street = 7;
    public static final Integer dancas_flocloricas = 8;


    @Column(name = "horario_inicio", nullable = false)
    private LocalTime horarioInicio;

    @Column(name = "horario_fim", nullable = false)
    private LocalTime horarioFim;

    @Column(name = "dia_semana", nullable = false)
    private Integer diaSemana;

    @Column(name = "lista_ids_alunos", nullable = true)
    private String listaIdsAlunos;

    @ManyToOne
    @JoinColumn(name = "id_Professor", nullable = false)
    private Usuario idProfessor;

    @Column(name = "sala", nullable = false)
    private String sala;

    @Column(name = "max_alunos", nullable = false)
    private Integer maxAlunos;

    @Column(name = "status", nullable = false)
    private Integer status;
    public static final Integer ativo = 1;
    public static final Integer inativo = 0;
}

