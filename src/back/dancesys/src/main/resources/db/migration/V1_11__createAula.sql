CREATE TABLE Aula (
    id INT IDENTITY(1,1) PRIMARY KEY,
    dia_semana INT NOT NULL,
    horario_inicio TIME NOT NULL,
    horario_fim TIME NOT NULL,
    max_alunos INT NOT NULL,
    nivel INT NOT NULL,
    status INT NOT NULL
);

ALTER TABLE Aula ADD id_Sala INT NOT NULL;

ALTER TABLE Aula ADD CONSTRAINT fk_Aula_Sala
    FOREIGN KEY (id_Sala) REFERENCES Sala(id);

ALTER TABLE Aula ADD id_Modalidade INT NOT NULL;

ALTER TABLE Aula ADD CONSTRAINT fk_Aula_Modalidade
    FOREIGN KEY (id_Modalidade) REFERENCES Modalidade(id);

ALTER TABLE Aula ADD id_Professor INT NOT NULL;

ALTER TABLE Aula ADD CONSTRAINT fk_Aula_Professor
    FOREIGN KEY (id_Professor) REFERENCES Professor(id);