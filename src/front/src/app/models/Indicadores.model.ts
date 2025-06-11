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