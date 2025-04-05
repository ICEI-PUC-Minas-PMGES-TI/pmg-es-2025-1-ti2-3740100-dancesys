CREATE TABLE Usuario (
    id INT IDENTITY(1,1) PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    cpf CHAR(11) NOT NULL UNIQUE,
    numero CHAR(11) NOT NULL UNIQUE,
    email VARCHAR(255) NOT NULL UNIQUE,
    senha VARCHAR(30) NOT NULL,
    tipo INT NOT NULL,
    status INT NOT NULL,
    endereco VARCHAR(255) NOT NULL,
    url_foto VARCHAR(255),
    data_nascimento DATE NOT NULL,
    criado_em DATE NOT NULL
);
