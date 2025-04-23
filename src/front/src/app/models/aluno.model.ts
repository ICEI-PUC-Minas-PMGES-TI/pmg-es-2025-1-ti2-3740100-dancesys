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
	usuario: Usuario;
	modalidades: ModalidadeAlunoNivel[];
}
