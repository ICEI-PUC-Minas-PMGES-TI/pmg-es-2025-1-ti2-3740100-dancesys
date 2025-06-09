package com.dancesys.dancesys.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "Aula_Experimental")
@Getter
@Setter
public class AulaExperimental {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "data_horario_inicio", nullable = false)
    private LocalDateTime dataHorarioInicio;

    @Column(name = "data_horario_fim", nullable = false)
    private LocalDateTime dataHorarioFim;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "cpf", nullable = false)
    private String cpf;

    @Column(name = "numero", nullable = false)
    private String numero;

    @Column(name = "situacao", nullable = true)
    private Integer situacao;
    public static final Integer PENDENTE = 1;
    public static final Integer CONVERTIDO = 2;
    public static final Integer RECUSADO = 3;

    @Column(name = "motivo", nullable = true)
    private Integer motivo;
    public static final Integer INTERESSE = 1;
    public static final Integer FINANCEIRO = 2;
    public static final Integer OUTRO = 3;

    @Column(name = "motivo_outro", nullable = true)
    private String motivoOutro;

    @ManyToOne
    @JoinColumn(name = "id_Professor", nullable = false)
    private Professor idProfessor;
}
