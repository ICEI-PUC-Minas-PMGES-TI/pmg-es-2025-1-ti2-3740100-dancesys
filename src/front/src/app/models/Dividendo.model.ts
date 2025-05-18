export class DividendoFilter{
    criadoEm!: string;
    pagoEm!: string;
    alunos!: number[];
    status!: number[];
    tipos!: number[];
    tamanho!: number;
    pagina!: number;
    orderBy!: string;
    order!: string;
}

export class DividendoResponse{
    conteudo!: [];
    total!: number;
}