CREATE TABLE Aula_Ocorrencia (
    id INT IDENTITY(1,1) PRIMARY KEY,
    codigo VARCHAR(20) NOT NULL,
    data DATE NOT NULL,
    status INT NOT NULL,
    motivo_cancelamento VARCHAR(255)
);

ALTER TABLE Aula_Ocorrencia ADD id_Aula INT NOT NULL;

ALTER TABLE Aula_Ocorrencia ADD CONSTRAINT fk_Aula_Ocorrencia_Aula
    FOREIGN KEY (id_Aula) REFERENCES Aula(id);

