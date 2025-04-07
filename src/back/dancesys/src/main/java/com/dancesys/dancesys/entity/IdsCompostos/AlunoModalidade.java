package com.dancesys.dancesys.entity.IdsCompostos;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class AlunoModalidade implements Serializable {
    @Column(name = "id_Aluno")
    private Long idAluno;

    @Column(name = "id_Modalidade")
    private Long idModalidade;
}