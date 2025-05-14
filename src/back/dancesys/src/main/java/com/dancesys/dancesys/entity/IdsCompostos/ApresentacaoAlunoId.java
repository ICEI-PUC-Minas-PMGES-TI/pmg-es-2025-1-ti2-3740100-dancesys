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
public class ApresentacaoAlunoId  implements Serializable {
    @Column(name = "id_Apresentacao")
    private Long idApresentacao;

    @Column(name = "id_Aluno")
    private Long idAluno;
}
