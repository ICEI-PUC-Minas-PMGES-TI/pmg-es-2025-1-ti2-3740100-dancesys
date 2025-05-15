CREATE TABLE Apresentacao_Aluno (
    id_Apresentacao INT NOT NULL,
    id_Aluno INT NOT NULL,
    CONSTRAINT fk_Apresentacao_Aluno_Apresentacao FOREIGN KEY (id_Apresentacao) REFERENCES Apresentacao_Evento(id),
    CONSTRAINT fk_Apresentacao_Aluno_Aluno FOREIGN KEY (id_Aluno) REFERENCES Aluno(id)
);