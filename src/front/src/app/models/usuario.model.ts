export class Usuario {

	id!: number;
	nome!: string;
	cpf!: string;
	numero!: string;
	email!: string;
	senha!: string;
	enumTipo!: number;
	creditos!: number;
	status!: number;
	urlFoto!: string;
	dataNascimento!: Date;
	criadoEm!: Date;
	modalidades!: string;
	experiencia!: number;
	boolBaile!: number;
	endereco!: string;

}

export interface UsuarioCookie {
	nome: string;
	id: number;
	enumTipo: number;
	status: number;
	urlFoto: string;
}

export enum UsuarioTipos {
	ALUNO_FIXO = 3,
	ALUNO_LIVRE = 4,
	ADMIN = 1,
	FUNCIONARIO = 2
}
