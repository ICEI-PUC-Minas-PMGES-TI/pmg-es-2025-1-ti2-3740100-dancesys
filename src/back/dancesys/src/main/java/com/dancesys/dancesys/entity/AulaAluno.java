package com.dancesys.dancesys.entity;

import com.dancesys.dancesys.entity.IdsCompostos.AulaAlunoId;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Aula_Aluno")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AulaAluno {

    @EmbeddedId
    private AulaAlunoId id;

    @ManyToOne
    @MapsId("idAula")
    @JoinColumn(name = "id_Aula", nullable = false)
    @JsonBackReference
    private Aula idAula;

    @ManyToOne
    @MapsId("idAluno")
    @JoinColumn(name = "id_Aluno", nullable = false)
    private Aluno idAluno;
}
