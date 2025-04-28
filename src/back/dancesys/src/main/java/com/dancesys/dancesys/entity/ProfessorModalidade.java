package com.dancesys.dancesys.entity;

import com.dancesys.dancesys.entity.IdsCompostos.ProfessorModalidadeId;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Professor_Modalidade")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProfessorModalidade {
    @EmbeddedId
    private ProfessorModalidadeId id;

    @ManyToOne
    @MapsId("idProfessor")
    @JoinColumn(name = "id_Professor", nullable = false)
    @JsonBackReference
    private Professor idProfessor;

    @ManyToOne
    @MapsId("idModalidade")
    @JoinColumn(name = "id_Modalidade", nullable = false)
    private Modalidade idModalidade;
}
