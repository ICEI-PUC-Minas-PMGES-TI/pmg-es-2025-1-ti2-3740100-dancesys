package com.dancesys.dancesys.entity;

import com.dancesys.dancesys.entity.IdsCompostos.ApresentacaoAlunoId;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Table(name = "Apresentacao_Aluno")
@Entity(name = "Apresentacao_Aluno")
@Getter
@Setter
public class ApresentacaoAluno {
    @EmbeddedId
    private ApresentacaoAlunoId id;

    @ManyToOne
    @MapsId("idApresentacao")
    @JoinColumn(name = "id_Apresentacao")
    @JsonIgnore
    private ApresentacaoEvento idApresentacao;

    @ManyToOne
    @MapsId("idALuno")
    @JoinColumn(name = "id_Aluno")
    private Aluno idAluno;

}
