export type Evento = {
	id: number;
	nome: string;
	local: string;
	dataHoraInicio: Date;
	dataHoraFim: Date;
	valor: number;
	urlFoto?: string;
};
