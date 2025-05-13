create TABLE Figurino_Apresentacao(
    id INT IDENTITY(1,1) PRIMARY KEY,
    status INT NOT NULL,
    tamanho VARCHAR(2) NOT NULL,
    codigo VARCHAR(45) NOT NULL
)

ALTER TABLE Figurino_Apresentacao
    ADD id_Figurino INT NOT NULL;

ALTER TABLE Figurino_Apresentacao
    ADD CONSTRAINT fk_Figurino_Apresentacao_Figurino
        FOREIGN KEY (id_Figurino) REFERENCES Figurino (id);

ALTER TABLE Figurino_Apresentacao
    ADD id_Aluno INT NOT NULL;

ALTER TABLE Figurino_Apresentacao
    ADD CONSTRAINT fk_Figurino_Apresentacao_Aluno
        FOREIGN KEY (id_Aluno) REFERENCES Aluno (id);

ALTER TABLE Figurino_Apresentacao
    ADD id_Apresentacao_Evento INT NOT NULL;

ALTER TABLE Figurino_Apresentacao
    ADD CONSTRAINT fk_Figurino_Apresentacao_Apresentacao_Evento
        FOREIGN KEY (id_Apresentacao_Evento) REFERENCES Apresentacao_Evento (id);