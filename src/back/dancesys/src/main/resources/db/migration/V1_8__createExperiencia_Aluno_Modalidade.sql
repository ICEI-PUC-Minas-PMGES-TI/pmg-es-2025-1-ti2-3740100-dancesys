CREATE TABLE Experiencia_Aluno_Modalidade (
    id INT IDENTITY(1,1) PRIMARY KEY,
    modalidade INT NOT NULL,
    nivel INT NOT NULL
);

ALTER TABLE Experiencia_Aluno_Modalidade ADD id_Aluno INT NOT NULL;

ALTER TABLE Chamada_Aula ADD CONSTRAINT fk_Experiencia_Aluno_Modalidade_Aluno
    FOREIGN KEY (id_Aluno) REFERENCES Usuario(id);
