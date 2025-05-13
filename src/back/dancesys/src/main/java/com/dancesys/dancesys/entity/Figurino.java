package com.dancesys.dancesys.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Table(name = "Figurino")
@Entity(name = "Figurino")
@Getter
@Setter
public class Figurino {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "tipo", nullable = false)
    private Integer tipo;

    @Column(name = "valor", nullable = false)
    private BigDecimal valor;

    @Column(name = "url_foto", nullable = true)
    private String urlFoto;
}