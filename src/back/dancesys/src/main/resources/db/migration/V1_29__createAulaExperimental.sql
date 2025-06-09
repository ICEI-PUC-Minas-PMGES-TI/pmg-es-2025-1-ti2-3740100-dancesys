CREATE TABLE Aula_Experimetal
(
    id INT IDENTITY(1,1) PRIMARY KEY,
    dataHorarioInico DATETIME2 NOT NULL,
    dataHorarioFim DATETIME2 NOT NULL,
    nome VARCHAR(255) NOT NULL,
    numero CHAR(11) NOT NULL ,
    cpf CHAR(11) NOT NULL,
    situacao INT,
    motivo INT,
    motivo_outro VARCHAR(255),
    id_Professor INT NOT NULL,
    CONSTRAINT fk_Aula_Experimental_Professor FOREIGN KEY (id_Professor) REFERENCES Professor (id),
);
