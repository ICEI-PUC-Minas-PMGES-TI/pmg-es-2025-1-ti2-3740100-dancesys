package com.dancesys.dancesys.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Table(name = "Aluno")
@Entity(name = "Aluno")
@Getter
@Setter
public class Aluno {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "creditos", nullable = false)
    private Integer creditos;
    public static final Integer max_creditos = 8;

    @Column(name = "bool_baile", nullable = false)
    private Integer boolBaile;
    public static final Integer sim = 1;
    public static final Integer nao = 0;

    @Column(name = "tipo", nullable = false)
    private Integer tipo;

    public static final Integer fixo = 1;
    public static final Integer livre = 2;

    @OneToOne
    @JoinColumn(name = "id_Usuario", nullable = false)
    private Usuario idUsuario;
}
