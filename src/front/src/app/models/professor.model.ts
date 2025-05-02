import { ItensPorPagina } from "../components/simple-table/simple-table.component";
import { Usuario } from "./usuario.model";

export interface Professor {
	id: number;
	informacoesProfissionais: string;
	valorHoraExtra: number;
	usuario: Usuario;
	modalidades: number[]; // correspondente aos ids das modalidades
}

export interface ProfessorFiltro {
	nome: string;
	cpf: string;
	email: string;
	status: 0 | 1 | null;
	tamanho: ItensPorPagina;
	pagina: number;
}
