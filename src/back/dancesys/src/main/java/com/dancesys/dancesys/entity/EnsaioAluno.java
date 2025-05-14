package com.dancesys.dancesys.entity;

import com.dancesys.dancesys.entity.IdsCompostos.EnsaioAlunoId;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "Ensaio_Aluno")
@Table(name = "Ensaio_Aluno")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EnsaioAluno {
    @EmbeddedId
    private EnsaioAlunoId id;

    @ManyToOne
    @MapsId("idEnsaio")
    @JoinColumn(name = "id_Ensaio", nullable = false)
    @JsonIgnore
    private EnsaioApresentacao idEnsaio;

    @ManyToOne
    @MapsId("idAluno")
    @JoinColumn(name = "id_Aluno", nullable = false)
    private Aluno idAluno;
}
