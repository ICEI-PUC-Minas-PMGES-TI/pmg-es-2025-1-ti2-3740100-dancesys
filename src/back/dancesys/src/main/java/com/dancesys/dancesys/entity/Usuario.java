package com.dancesys.dancesys.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;


@Table(name = "Usuario")
@Entity(name = "Usuario")
@Getter
@Setter
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "cpf", nullable = false, unique = true)
    private String cpf;

    @Column(name = "numero", nullable = false, unique = true)
    private String numero;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "senha", nullable = false)
    private String senha;

    @Column(name = "tipo", nullable = false)
    public Integer tipo;
    public static final Integer admin = 1;
    public static final Integer funcionario = 2;
    public static final Integer aluno= 3;

    @Column(name = "status", nullable = false)
    private Integer status;
    public static final Integer ativo = 1;
    public static final Integer desativo = 0;

    @Column(name = "endereco",nullable = false)
    private String endereco;

    @Column(name = "url_foto", nullable = true)
    private String urlFoto;

    @Column(name = "data_nascimento", nullable = false)
    private LocalDate dataNascimento;

    @Column(name = "criado_em", nullable = false)
    private LocalDate criadoEm;
}

