CREATE TABLE Chamada (
    presenca INT NOT NULL
);

ALTER TABLE Chamada ADD id_Aluno INT NOT NULL;

ALTER TABLE Chamada ADD CONSTRAINT fk_Chamada_Aluno
    FOREIGN KEY (id_Aluno) REFERENCES Aluno(id);

ALTER TABLE Chamada ADD id_Aula_Ocorrencia INT NOT NULL;

ALTER TABLE Chamada ADD CONSTRAINT fk_Chamada_Aula_Ocorrencia
    FOREIGN KEY (id_Aula_Ocorrencia) REFERENCES Aula_Ocorrencia(id);