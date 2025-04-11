CREATE TABLE Dividendo (
    id INT IDENTITY(1,1) PRIMARY KEY,
    valor DECIMAL(9,2) NOT NULL,
    criado_em DATE NOT NULL,
    pago_em DATE,
    tipo INT NOT NULL,
    status INT NOT NULL,
    codigo VARCHAR(40) NOT NULL
);

ALTER TABLE Dividendo ADD id_Aluno INT NOT NULL;

ALTER TABLE Dividendo ADD CONSTRAINT fk_Dividendo_Aluno
    FOREIGN KEY (id_Aluno) REFERENCES Aluno(id);