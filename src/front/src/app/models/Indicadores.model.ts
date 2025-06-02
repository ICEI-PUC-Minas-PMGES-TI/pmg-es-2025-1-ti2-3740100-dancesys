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