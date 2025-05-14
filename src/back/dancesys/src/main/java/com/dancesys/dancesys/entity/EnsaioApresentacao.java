package com.dancesys.dancesys.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Table(name = "Ensaio_Apresentacao")
@Entity(name = "Ensaio_Apresentacao")
@Getter
@Setter
public class EnsaioApresentacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "data_hora_inicio", nullable = false)
    private LocalDateTime dataHoraInicio;

    @Column(name = "data_hora_fim", nullable = false)
    private LocalDateTime dataHoraFim;

    @ManyToOne
    @JoinColumn(name = "id_Professor", nullable = false)
    private Professor idProfessor;

    @ManyToOne
    @JoinColumn(name = "id_Apresentacao_Evento", nullable = false)
    private ApresentacaoEvento idApresentacaoEvento;

    @OneToMany(mappedBy = "idEnsaio", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<EnsaioAluno> alunos;

}