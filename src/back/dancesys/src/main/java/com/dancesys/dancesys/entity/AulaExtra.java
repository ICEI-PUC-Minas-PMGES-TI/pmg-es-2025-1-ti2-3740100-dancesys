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

    @Column(name = "motivo")
    private String motivo;

    @Column(name = "codigo")
    private String codigo;

    @Column(name = "id_professor", nullable = false)
    private Long idProfessor;

    @Column(name = "id_sala")
    private Long idSala;

    @Column(name = "id_aluno", nullable = false)
    private Long idAluno;
}