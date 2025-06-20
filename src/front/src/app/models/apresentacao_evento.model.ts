import { Aluno } from "./aluno.model";

export class ApresentacaoEvento {
	id!: number;
	nome!: string;
	horaInicio!: Date;
	horaFim!: Date;
	idEvento!: number;
	eventoNome!: string;
	alunos!: number[];
}

export type ApresentacaoEventoResponse = {
	conteudo: ApresentacaoEvento[];
	total: number;
};

export type ApresentacaoEventoFilter = {
	nome: string | null;
	idEvento: number | null;
	alunos: number[] | null;
	tamanho: number | 0;
	pagina: number | 0;
	orderBy: string| null;
	order: string | null;
};