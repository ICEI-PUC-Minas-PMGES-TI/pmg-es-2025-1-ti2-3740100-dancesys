export class Usuario {
	public id!: number; // Long
	public nome!: string; // String
	public cpf!: string; // String
	public numero!: string; // String
	public email!: string; // String
	public senha!: string; // String
	public tipo!: number; // Integer
	public status!: boolean; // Boolean
	public endereco!: string; // String
	public urlFoto!: string; // String
	public dataNascimento!: Date; // LocalDate
	public criadoEm!: Date; // LocalDate

	public static getTipoString(t: number): string {
		switch (t) {
			case UsuarioTipos.ADMIN:
				return "Admin";
			case UsuarioTipos.ALUNO:
				return "Aluno";
			case UsuarioTipos.FUNCIONARIO:
				return "Funcionário";
			default:
				return "Unknown";
		}
	}

	public static getStatusString(s: boolean): string {
		if (s) {
			return "Ativo";
		}
		return "Inativo";
	}
}

export interface UsuarioCookie {
	nome: string;
	id: number;
	tipo: number;
	status: boolean;
	urlFoto: string;
}

export interface UsuarioFiltro {
	nome?: string;
	email: string;
	tipo: UsuarioTipos;
	status: 0 | 1;
	cpf: string;
}

export enum UsuarioTipos {
	ADMIN = 1,
	FUNCIONARIO = 2,
	ALUNO = 3,
}
