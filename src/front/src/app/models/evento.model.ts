export type Evento = {
	id?: number;
	nome: string;
	local: string;
	dataHoraInicio: Date;
	dataHoraFim: Date;
	valor: number;
	urlFoto?: string;
	imgBase64: string;
	nomeArquivo: string;
};

export type EventoResponse = {
	conteudo: Evento[];
	total: number;
};

export type EventoFilter = {
	nome: string | null;
	local: string | null;
	data: Date | null;
	alunos: number[] | null;
	pagina: number;
	tamanho: number;
	orderBy: string;
	order: string;
};
