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
    dataInicio!: Date;
    dataFim!: Date;
    pagina!: number;
    tamanho!: number;
    orderBy!: string;
    order!: string;
}