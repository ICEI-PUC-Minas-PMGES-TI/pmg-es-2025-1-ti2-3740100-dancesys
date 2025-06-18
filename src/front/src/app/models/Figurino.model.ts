export type FigurinoFilter = {
    nome: string | null;
    valor: number | null;
    tipo: number | null;
    pagina: number | null;
    tamanho: number | null;
    orderBy: number | null;
    order: number | null;
}

export type Figurino = {
    id: number | null;
    nome: string | null;
    tipo: number | null;
    valor: number | null;
    base64: String | null;
    nomeArquivo: String | null;
    urlFoto: String | null;
}