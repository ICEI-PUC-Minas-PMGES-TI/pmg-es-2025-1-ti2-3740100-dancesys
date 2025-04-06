CREATE TABLE Professor_Modalidade (
    id_Professor INT NOT NULL,
    id_Modalidade INT NOT NULL,
    CONSTRAINT fk_Professor_Modalidade_Professor FOREIGN KEY (id_Professor) REFERENCES Professor(id),
    CONSTRAINT fk_Professor_Modalidade_Modalidade FOREIGN KEY (id_Modalidade) REFERENCES Modalidade(id)
);
