CREATE TABLE Modalidade_Aluno_Nivel (
    nivel INT NOT NULL
);

ALTER TABLE Modalidade_Aluno_Nivel ADD id_Aluno INT NOT NULL;

ALTER TABLE Modalidade_Aluno_Nivel ADD CONSTRAINT fk_Modalidade_Aluno_Nivel_Aluno
    FOREIGN KEY (id_Aluno) REFERENCES Aluno(id);

ALTER TABLE Modalidade_Aluno_Nivel ADD id_Modalidade INT NOT NULL;

ALTER TABLE Modalidade_Aluno_Nivel ADD CONSTRAINT fk_Modalidade_Aluno_Nivel_Modalidade
    FOREIGN KEY (id_Modalidade) REFERENCES Modalidade(id);