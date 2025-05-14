package com.dancesys.dancesys.entity.IdsCompostos;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class EnsaioAlunoId implements Serializable {
    @Column(name = "id_Ensaio")
    private Long idEnsaio;

    @Column(name = "idAluno")
    private Long idAluno;
}
