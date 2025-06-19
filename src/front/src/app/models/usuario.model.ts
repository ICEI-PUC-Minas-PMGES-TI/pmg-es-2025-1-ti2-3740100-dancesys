export interface Usuario {
	id: number; // Long
	nome: string; // String
	cpf: string; // String
	numero: string; // String
	email: string; // String
	senha: string; // String
	tipo: number; // Integer
	status: boolean; // Boolean
	endereco: string; // String
	urlFoto: string | null; // String
	base64: string | null;
	nomeArquivo: string | null;
	dataNascimento: Date; // LocalDate
	criadoEm: Date; // LocalDate
}

export interface UsuarioCookie {
	id: number;
	tipo: number;
}

export interface UsuarioFiltro {
	nome?: string;
	email: string;
	tipo?: UsuarioTipos;
	status: 0 | 1 | "";
	cpf: string;
}

export enum UsuarioTipos {
	ADMIN = 1,
	FUNCIONARIO = 2,
	ALUNO = 3,
}
