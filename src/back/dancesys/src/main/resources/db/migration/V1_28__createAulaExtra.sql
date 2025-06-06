CREATE TABLE Aula_Extra
(
    id INT IDENTITY(1,1) PRIMARY KEY,
    horario_inicio DATETIME2 NOT NULL,
    horario_fim DATETIME2 NOT NULL,
    situacao INT NOT NULL,
    motivo VARCHAR(255),
    codigo VARCHAR(30),
    id_Professor INT NOT NULL,
    id_Sala INT,
    id_Aluno INT NOT NULL,
    CONSTRAINT fk_Aula_Extra_Professor FOREIGN KEY (id_Professor) REFERENCES Professor (id),
    CONSTRAINT fk_Aula_Extra_Sala FOREIGN KEY (id_Sala) REFERENCES Sala (id),
    CONSTRAINT fk_Aula_Extra_Aluno FOREIGN KEY (id_Aluno) REFERENCES Aluno (id)
);
