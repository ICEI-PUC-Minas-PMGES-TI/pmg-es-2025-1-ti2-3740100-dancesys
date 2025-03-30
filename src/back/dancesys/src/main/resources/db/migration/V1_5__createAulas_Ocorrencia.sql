CREATE TABLE Aula_Ocorrencia (
    id INT IDENTITY(1,1) PRIMARY KEY,
    codigo_aula VARCHAR(255) NOT NULL,
    lista_ids_alunos VARCHAR(255),
    data DATE NOT NULL,
    status INT NOT NULL,
    motivo_cancelamento VARCHAR(255)
);

ALTER TABLE Aula_Ocorrencia ADD id_Aula INT NOT NULL;

ALTER TABLE Aula_Ocorrencia ADD CONSTRAINT fk_Aula_Ocorrencia_Aula
    FOREIGN KEY (id_Aula) REFERENCES Aula(id);