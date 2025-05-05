CREATE TABLE Evento(
    id INT IDENTITY(1,1) PRIMARY KEY,
    nome VARCHAR(255) NOT NUll,
    local varchar(255) NOT NULL,
    dataHora_inicio DATETIME2(0) NOT NULL,
    dataHora_fim DATETIME2(0) NOT NULL,
    valor DECIMAL(9,2) NOT NULL,
    url_foto VARCHAR(255)
);
