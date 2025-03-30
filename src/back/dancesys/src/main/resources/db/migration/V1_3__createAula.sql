CREATE TABLE Aula (
    id INT IDENTITY(1,1) PRIMARY KEY,
    tipo INT NOT NULL,
    horario_inicio TIME NOT NULL,
    horario_fim TIME NOT NULL,
    dia_semana INT NOT NULL,
    lista_ids_alunos VARCHAR(255),
);

ALTER TABLE Aula ADD id_Professor INT NOT NULL;

ALTER TABLE Aula ADD CONSTRAINT fk_Aula_Professor
FOREIGN KEY (id_Professor) REFERENCES Usuario(id);
