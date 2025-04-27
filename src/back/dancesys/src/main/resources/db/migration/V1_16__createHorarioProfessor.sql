CREATE TABLE Horario_Professor(
    id INT IDENTITY(1,1) PRIMARY KEY,
    dia_semana INT NOT NULL,
    horario_entrada TIME NOT NULL,
    horario_saida TIME NOT NULL
);

ALTER TABLE Horario_Professor ADD id_Professor INT NOT NULL;

ALTER TABLE  Horario_Professor ADD CONSTRAINT fk_Horario_Professor_Professor
    FOREIGN KEY (id_Professor) REFERENCES Professor(id);
