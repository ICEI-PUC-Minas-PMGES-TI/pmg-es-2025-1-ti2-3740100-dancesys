package com.dancesys.dancesys.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Table(name = "Dividendo")
@Entity(name = "Dividendo")
@Getter
@Setter
public class Dividendo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "valor", nullable = false)
    private BigDecimal valor;
    public static final BigDecimal VALOR_MATRICULA = BigDecimal.valueOf(150.00);
    public static final BigDecimal VALOR_MENSALIDADE = BigDecimal.valueOf(350.00);

    @Column(name = "criado_em", nullable = false)
    private LocalDate criadoEm;

    @Column(name = "pago_em", nullable = true)
    private LocalDate pagoEm;

    @Column(name = "tipo", nullable = false)
    private Integer tipo;
    public static final Integer MATRICULA = 1;
    public static final Integer MENSALIDADE = 2;
    public static final Integer AULA = 3;
    public static final Integer EVENTO = 4;
    public static final Integer FIGURINO = 5;

    @Column(name = "status", nullable = false)
    private Integer status;
    public static final Integer pendente = 1;
    public static final Integer pago = 2;
    public static final Integer atrasado = 3;

    @Column(name = "codigo", nullable = false)
    private String codigo;

    @JsonProperty("mesesAtraso")
    public Long getMesesAtraso() {
        return status.equals(atrasado) ?  ChronoUnit.MONTHS.between(criadoEm, LocalDate.now()) : 0;
    }

    @ManyToOne
    @JoinColumn(name = "id_Aluno", nullable = false)
    private Aluno idAluno;
}
