CREATE TABLE Ensaio_Aluno (
    id_Ensaio INT NOT NULL,
    id_Aluno INT NOT NULL,
    CONSTRAINT fk_Ensaio_Aluno_Ensaio FOREIGN KEY (id_Ensaio) REFERENCES Ensaio_Apresentacao (id),
    CONSTRAINT fk_Ensaio_Aluno_Aluno FOREIGN KEY (id_Aluno) REFERENCES Aluno(id)
);
