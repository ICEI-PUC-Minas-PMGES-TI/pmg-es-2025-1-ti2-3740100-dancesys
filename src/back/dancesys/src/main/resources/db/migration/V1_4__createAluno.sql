CREATE TABLE Aluno (
    id INT IDENTITY(1,1) PRIMARY KEY,
    creditos INT NOT NULL,
    boolBaile INT NOT NULL,
    tipo INT NOT NULL,
);

ALTER TABLE Aluno ADD id_Usuario INT NOT NULL;

ALTER TABLE Aluno ADD CONSTRAINT fk_Aluno_Usuario
    FOREIGN KEY (id_Usuario) REFERENCES Usuario(id);