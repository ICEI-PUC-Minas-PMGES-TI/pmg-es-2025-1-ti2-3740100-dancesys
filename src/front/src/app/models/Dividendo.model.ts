import { AlunoResponse } from "../services/admin.service";

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

export type Dividendo = {
    id: number;
    valor: number;
    criadoEm: Date;
    pagoEm: Date;
    tipo: number;
    status: number;
    codigo: string;
    mesesAtrasado: number;
    idAluno: AlunoResponse;
}