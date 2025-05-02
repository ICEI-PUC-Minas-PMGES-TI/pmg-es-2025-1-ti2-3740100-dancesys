import { Usuario } from "./usuario.model";

export interface Professor {
	id: number;
	informacoesProfissionais: string;
	valorHoraExtra: number;
	usuario: Usuario;
	modalidades: number[]; // correspondente aos ids das modalidades
}

export interface ProfessorFiltro {
	nome?: string;
	email: string;
	status: 0 | 1;
	cpf: string;
}
