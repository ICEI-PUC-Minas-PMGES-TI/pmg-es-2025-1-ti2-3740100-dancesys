package com.dancesys.dancesys.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "Aula_Experimetal")
@Getter
@Setter
public class AulaExperimental {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "dataHorarioInicio", nullable = false)
    private LocalDateTime dataHorarioInicio;

    @Column(name = "dataHorarioFim", nullable = false)
    private LocalDateTime dataHorarioFim;

    @Column(name = "cpf", nullable = false)
    private String cpf;

    @Column(name = "numero", nullable = false)
    private String numero;

    @Column(name = "situacao", nullable = true)
    private Integer situacao;

    @Column(name = "motivo", nullable = true)
    private Integer motivo;

    @Column(name = "motivo_outro", nullable = true)
    private String motivoOutro;

    @ManyToOne
    @JoinColumn(name = "id_Professor", nullable = false)
    private Professor idProfessor;
}
