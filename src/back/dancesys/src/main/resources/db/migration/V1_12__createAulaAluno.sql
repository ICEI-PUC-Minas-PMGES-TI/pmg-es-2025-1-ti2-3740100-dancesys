CREATE TABLE Aula_Aluno (
    id_Aula INT NOT NULL,
    id_Aluno INT NOT NULL,
    CONSTRAINT fk_Aula_Aluno_Aula FOREIGN KEY (id_Aula) REFERENCES Aula(id),
    CONSTRAINT fk_Aula_Aluno_Aluno FOREIGN KEY (id_Aluno) REFERENCES Aluno(id)
);