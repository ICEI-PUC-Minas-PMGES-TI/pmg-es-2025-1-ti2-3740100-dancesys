package com.dancesys.dancesys.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;
import java.util.List;

@Table(name = "Apresentacao_Evento")
@Entity(name = "Apresentacao_Evento")
@Getter
@Setter
public class ApresentacaoEvento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "hora_inicio", nullable = false)
    private LocalTime HoraInicio;

    @Column(name = "hora_fim", nullable = false)
    private LocalTime HoraFim;

    @ManyToOne
    @JoinColumn(name = "id_Evento", nullable = false)
    @JsonIgnore
    private Evento idEvento;

    @JsonProperty("idEvento")
    public Long getIdEventoOnly() {
        return idEvento != null ? idEvento.getId() : null;
    }
    @JsonProperty("eventoNome")
    public String getNomeEventoOnly() {
        return idEvento != null ? idEvento.getNome() : null;
    }

    @Column(name = "nome", nullable = false)
    private String nome;

    @OneToMany(mappedBy = "idApresentacao", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ApresentacaoAluno> alunos;
}