create TABLE Figurino(
    id INT IDENTITY(1,1) PRIMARY KEY,
    nome VARCHAR(120) NOT NULL,
    tipo INT NOT NULL,
    valor DECIMAL(9,2) NOT NULL,
    url_foto VARCHAR(255)
)