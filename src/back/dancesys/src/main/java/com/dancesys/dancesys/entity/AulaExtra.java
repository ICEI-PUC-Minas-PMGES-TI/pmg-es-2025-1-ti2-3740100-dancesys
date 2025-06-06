package com.dancesys.dancesys.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "Aula_Extra")
@Getter
@Setter
public class AulaExtra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "horario_inicio", nullable = false)
    private LocalDateTime horarioInicio;

    @Column(name = "horario_fim", nullable = false)
    private LocalDateTime horarioFim;

    @Column(name = "situacao", nullable = false)
    private Integer situacao;
    public final static Integer PENDENTE = 1;
    public final static Integer ACEITA = 2;
    public final static Integer INDEFERIDA = 3;
    public final static Integer CANCELADA = 4;

    @Column(name = "motivo")
    private String motivo;

    @Column(name = "codigo")
    private String codigo;

    @ManyToOne
    @JoinColumn(name = "id_Professor", nullable = false)
    private Professor idProfessor;

    @ManyToOne
    @JoinColumn(name = "id_Sala")
    private Sala idSala;

    @ManyToOne
    @JoinColumn(name = "id_Aluno", nullable = false)
    private Aluno idAluno;
}