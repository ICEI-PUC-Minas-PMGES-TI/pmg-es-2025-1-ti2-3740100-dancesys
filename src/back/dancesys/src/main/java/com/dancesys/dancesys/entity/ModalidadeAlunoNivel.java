package com.dancesys.dancesys.entity;

import com.dancesys.dancesys.entity.IdsCompostos.AlunoModalidade;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Modalidade_Aluno_Nivel")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ModalidadeAlunoNivel {

    @EmbeddedId
    private AlunoModalidade id;

    @ManyToOne
    @MapsId("idAluno")
    @JoinColumn(name = "id_Aluno", nullable = false)
    private Aluno idAluno;

    @ManyToOne
    @MapsId("idModalidade")
    @JoinColumn(name = "id_Modalidade", nullable = false)
    private Modalidade idModalidade;

    @Column(name = "nivel", nullable = false)
    private Integer nivel;

    public static final Integer BASICO = 1;
    public static final Integer INTERMEDIARIO = 2;
    public static final Integer AVANCADO = 3;
}
