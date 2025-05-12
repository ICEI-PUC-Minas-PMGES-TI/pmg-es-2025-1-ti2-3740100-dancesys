create TABLE Apresentacao_Evento(
    id INT IDENTITY(1,1) PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    hora_inicio TIME NOT NULL,
    hora_fim TIME NOT NULL
)

ALTER TABLE Apresentacao_Evento ADD id_Evento INT NOT NULL;

ALTER TABLE Apresentacao_Evento ADD CONSTRAINT fk_Apresentacao_Evento_Evento
    FOREIGN KEY (id_Evento) REFERENCES Evento(id);