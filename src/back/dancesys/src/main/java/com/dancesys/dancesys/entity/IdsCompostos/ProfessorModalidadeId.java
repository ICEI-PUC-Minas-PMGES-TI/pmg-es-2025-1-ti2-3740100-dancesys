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
public class ProfessorModalidadeId implements Serializable {
    @Column(name = "id_Professor")
    private Long idProfessor;

    @Column(name = "id_Modalidade")
    private Long idModalidade;
}
