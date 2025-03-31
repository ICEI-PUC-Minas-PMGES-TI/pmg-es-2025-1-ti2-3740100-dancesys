CREATE TABLE Chamada_Aula (
    id INT IDENTITY(1,1) PRIMARY KEY,
    presenca INT NOT NULL
);

ALTER TABLE Chamada_Aula ADD id_Aula_Ocorrencia INT NOT NULL;

ALTER TABLE Chamada_Aula ADD CONSTRAINT fk_Chamada_Aula_Aula_Ocorrencia
    FOREIGN KEY (id_Aula_Ocorrencia) REFERENCES Aula_Ocorrencia(id);

ALTER TABLE Chamada_Aula ADD id_Aluno INT NOT NULL;

ALTER TABLE Chamada_Aula ADD CONSTRAINT fk_Chamada_Aula_Aluno
    FOREIGN KEY (id_Aluno) REFERENCES Usuario(id);