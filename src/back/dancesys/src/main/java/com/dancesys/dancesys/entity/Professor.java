package com.dancesys.dancesys.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Table(name = "Professor")
@Entity(name = "Professor")
@Getter
@Setter
public class Professor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "informacoes_profissionais", nullable = false)
    private String informacoesProfissionais;

    @Column(name = "valor_hora_extra", nullable = false)
    private BigDecimal valorHoraExtra;

    @OneToOne
    @JoinColumn(name = "id_Usuario", nullable = false)
    private Usuario idUsuario;
}
