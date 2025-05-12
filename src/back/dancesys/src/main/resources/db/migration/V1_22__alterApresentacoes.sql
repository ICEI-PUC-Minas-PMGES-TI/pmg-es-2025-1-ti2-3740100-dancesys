ALTER TABLE Apresentacao_Evento
    DROP COLUMN nome;

ALTER TABLE Ensaio_Apresentacao
    ADD id_Apresentacao_Evento INT NOT NULL;

ALTER TABLE Ensaio_Apresentacao
    ADD CONSTRAINT fk_Ensaio_Apresentacao_Apresentacao_Evento
        FOREIGN KEY (id_Apresentacao_Evento) REFERENCES Apresentacao_Evento (id);