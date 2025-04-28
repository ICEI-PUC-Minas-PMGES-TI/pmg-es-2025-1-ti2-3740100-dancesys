import { Usuario } from "./usuario.model";

export interface Professor {
	id: number;
	informacoesProfissionais: string;
	valorHoraExtra: number;
	usuario: Usuario;
	modalidades: number[]; // correspondente aos ids das modalidades
}
