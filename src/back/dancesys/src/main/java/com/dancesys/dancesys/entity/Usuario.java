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

    @Column(name = "enum_tipo", nullable = false)
    private Integer enumTipo;

    @Column(name = "creditos", nullable = true)
    private Integer creditos;

    @Column(name = "status", nullable = false)
    private Integer status;

    @Column(name = "url_foto", nullable = true)
    private String urlFoto;

    @Column(name = "data_nascimento", nullable = false)
    private LocalDate dataNascimento;

    @Column(name = "criado_em", nullable = false)
    private LocalDate criadoEm;

    @Column(name = "valor_hora_extra",nullable = false, precision = 9, scale = 2)
    private BigDecimal valorHoraExtra;

}

