import { ItensPorPagina } from "../components/simple-table/simple-table.component";
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

export interface AlunoFiltro {
	nome: string;
	cpf: string;
	email: string;
	tipo: 1 | 2 | null;
	status: 0 | 1 | null;
	tamanho: ItensPorPagina;
	pagina: number;
}
