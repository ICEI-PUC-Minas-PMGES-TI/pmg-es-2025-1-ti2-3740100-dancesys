package com.dancesys.dancesys.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Table(name = "Ingresso_Evento")
@Entity(name = "Ingresso_Evento")
@Getter
@Setter
public class IngressoEvento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "tipo", nullable = false)
    private Integer tipo;

    @Column(name = "codigo", nullable = false)
    private String codigo;

    @Column(name = "quantidade", nullable = false)
    private Integer quantidade;

    @ManyToOne
    @JoinColumn(name = "id_Aluno", nullable = false)
    private Aluno idAluno;

    @ManyToOne
    @JoinColumn(name = "id_Evento", nullable = false)
    private Evento idEvento;
}