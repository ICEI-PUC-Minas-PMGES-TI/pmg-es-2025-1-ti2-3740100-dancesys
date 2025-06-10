import { Usuario } from "./usuario.model";

export interface Professor {
	id: number;
	informacoesProfissionais: string;
	valorHoraExtra: number;
	idUsuario: Usuario;
	modalidades: number[]; // correspondente aos ids das modalidades
}

export interface ProfessorFiltro {
	nome?: string;
	cpf?: string;
	email?: string;
	status?: 0 | 1;
	tamanho?: number;
	pagina?: number;
	orderBy?: string;
	order?: string;
}
