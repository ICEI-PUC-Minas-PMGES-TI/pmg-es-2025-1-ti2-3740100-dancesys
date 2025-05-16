export class Ensaio{
    id!: number;
    dataHoraInicio!: Date;
    dataHoraFim!: Date;
    idProfessor!: number;
    idApresentacaoEvento!: number;
    alunos!: number[];
}

export class EnsaioFilter{
    idProfessor!: number;
    apresentacoes!: number[];
    alunos!: number[];
    data!: Date;
    pagina!: number;
    tamanho!: number;
}