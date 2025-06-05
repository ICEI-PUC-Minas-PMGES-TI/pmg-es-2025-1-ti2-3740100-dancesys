import { AlunoResponse } from "../services/admin.service";

export type Dividendo = {
    id: number;
    valor: number;
    criadoEm: Date;
    pagoEm: Date;
    tipo: number;
    status: number;
    codigo: string;
    mesesAtrasado: number;
    idAluno: AlunoResponse
}