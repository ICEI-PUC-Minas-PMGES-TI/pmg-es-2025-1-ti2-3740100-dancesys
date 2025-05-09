CREATE TABLE Ensaio_Apresentacao
(
    id              INT IDENTITY(1,1) PRIMARY KEY,
    data_hora_inicio DATETIME2(0) NOT NULL,
    data_hora_fim    DATETIME2(0) NOT NULL
);

ALTER TABLE Ensaio_Apresentacao
    ADD id_Professor INT NOT NULL;

ALTER TABLE Ensaio_Apresentacao
    ADD CONSTRAINT fk_Ensaio_Apresentacao_Professor
        FOREIGN KEY (id_Professor) REFERENCES Professor (id);

/*
ALTER TABLE Ensaio_Apresentacao
    ADD CONSTRAINT fk_Ensaio_Apresentacao_Apresentacao_Evento
        FOREIGN KEY (id_Apresentacao_Evento) REFERENCES Apresentacao_Evento (id);
*/