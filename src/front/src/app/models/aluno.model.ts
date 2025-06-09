import { ModalidadeAlunoNivel } from "./modalidade.model";
import { Usuario } from "./usuario.model";

export enum TipoAluno {
	FIXO = 1,
	FLEXIVEL = 2,
}

export interface Aluno {
	id: number;
	creditos: number;
	boolBaile: boolean;
	tipo: TipoAluno.FIXO | TipoAluno.FLEXIVEL;
	idUsuario: Usuario;
	modalidades: ModalidadeAlunoNivel[];
}

export interface AlunoFilter {
	nome?: string;
	cpf?: string;
	email?: string;
	tipo?: TipoAluno;
	status?: 0 | 1;
	tamanho?: number;
	pagina?: number;
	orderBy?: string;
	order?: string;
}
