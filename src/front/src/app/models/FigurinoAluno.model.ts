export class FigurinoAluno{
    id!: number;
    status!: number;
    tamanho!: string;
    codigo!: string;
    idFigurino!: number;
    idAluno!: number;
    idApresentacaoEvento!: number;
}

export type FigurinoAlunoFilter = {
    idAluno: number;
    idFigurino: number;
    idEvento: number;
    idAApresentacao: number;
    pagina: number;
    tamanho: number;
    orderBy: string;
    order: string;
}