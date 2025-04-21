package com.dancesys.dancesys.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;
import java.util.List;

@Table(name = "Aula")
@Entity(name = "Aula")
@Getter
@Setter
public class Aula {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "dia_semana", nullable = false)
    private Integer diaSemana;

    @Column(name = "horario_inicio", nullable = false)
    private LocalTime horarioInicio;

    @Column(name = "horario_fim", nullable = false)
    private LocalTime horarioFim;

    @Column(name = "max_alunos", nullable = false)
    private Integer maxAlunos;

    @Column(name = "nivel", nullable = false)
    private Integer nivel;

    @Column(name = "status", nullable = false)
    private Integer status;
    public static final Integer ativo = 1;
    public static final Integer desativo = 0;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_Sala", nullable = false)
    private Sala idSala;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_Modalidade", nullable = false)
    private Modalidade idModalidade;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_Professor", nullable = false)
    private Professor idProfessor;

    @OneToMany(mappedBy = "idAula", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<AulaAluno> alunos;
}
