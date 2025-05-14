package com.dancesys.dancesys.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Table(name = "Figurino_Apresentacao",
        uniqueConstraints = {
            @UniqueConstraint(columnNames = {"idFigurino", "idApresentacaoEvento"})
        })
@Entity(name = "Figurino_Apresentacao")
@Getter
@Setter
public class FigurinoApresentacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "status", nullable = false)
    private Integer status;

    @Column(name = "tamanho", nullable = false)
    private String tamanho;

    @Column(name = "codigo", nullable = false)
    private String codigo;

    @ManyToOne
    @JoinColumn(name = "idFigurino", nullable = false)
    private Figurino idFigurino;

    @ManyToOne
    @JoinColumn(name = "idAluno", nullable = false)
    private Aluno idAluno;

    @ManyToOne
    @JoinColumn(name = "idApresentacaoEvento", nullable = false)
    private ApresentacaoEvento idApresentacaoEvento;
}