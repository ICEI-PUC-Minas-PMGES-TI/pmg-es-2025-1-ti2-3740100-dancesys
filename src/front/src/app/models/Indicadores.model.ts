export type Financeiro = {
    mes: number;
    tipo: number;
    boletosPagosSemAtraso: number;
    somaValoresSemAtraso: number;
    boletosPagosComAtraso: number;
    somaValoresComAtraso: number;
    mediaDiasAtraso: number;
    boletosNaoPagos: number;
}

export type Conversao = {
    mes: number;
    totalConvertido: number;
    totalRecusado: number;
    totalCriadas: number;
    totalFinalizadas: number;
    totalInteresse: number;
    totalFinanceiro: number;
    totalOutro: number;
}

export type Aulas = {
    mes: number;
    totalAulasOcorrentesRealizadas:  number;
    totalAulasOcorrentesCanceladas:  number;
    minutosAulasOcorrentes:  number;
    totalAulasExtrasRealizadas:  number;
    totalAulasExtrasCanceladas:  number;
    minutosAulasExtras:  number;
    totalAulasExperimentais:  number;
    minutosAulasExperimentais:  number;
}

export type AulasModalidade = {
    modalidade: string;
    mes: number;
    totalAulas: number;
}

export type AlunosModalidade = {
    modalidade: string;
    nivel: number;
    quantidadeAlunos: number;
}