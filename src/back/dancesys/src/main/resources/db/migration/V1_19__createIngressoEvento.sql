create TABLE Ingresso_Evento(
    id INT IDENTITY(1,1) PRIMARY KEY,
    tipo INT NOT NULL,
    codigo VARCHAR(40) NOT NULL,
    quantidade VARCHAR(45) NOT NULL
)

ALTER TABLE Ingresso_Evento ADD id_Aluno INT NOT NULL;

ALTER TABLE Ingresso_Evento ADD CONSTRAINT fk_Ingresso_Evento_Aluno
        FOREIGN KEY (id_Aluno) REFERENCES Aluno(id);

ALTER TABLE Ingresso_Evento ADD id_Evento INT NOT NULL;

ALTER TABLE Ingresso_Evento ADD CONSTRAINT fk_Ingresso_Evento_Evento
    FOREIGN KEY (id_Evento) REFERENCES Evento(id);