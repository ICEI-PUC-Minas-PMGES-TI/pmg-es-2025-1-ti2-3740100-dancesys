CREATE TABLE Professor (
    id INT IDENTITY(1,1) PRIMARY KEY,
    informacoes_profissionais VARCHAR(255),
    valor_hora_extra DECIMAL(9,2) NOT NULL
);

ALTER TABLE Professor ADD id_Usuario INT NOT NULL;

ALTER TABLE Professor ADD CONSTRAINT fk_Professor_Usuario
    FOREIGN KEY (id_Usuario) REFERENCES Usuario(id);