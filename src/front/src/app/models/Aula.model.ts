import { Time } from "@angular/common";

export class Aula {
	id!: number;
	diaSemana!: number;
	horarioInicio!: Time;
	horarioFim!: Time;
	maxAlunos!: number;
	status!: number;
	idSala!: number;
	idModalidade!: number;
	idProfessor!: number;
	alunos!: number[];
}

export class AulaFilter {
	dias!: number[];
	professores!: number[];
	modalidades!: number[];
	tamanho!: number;
	pagina!: number;
}

export interface AulaExtraDTO {
	horarioInicio: Date; // LocalDateTime
	horarioFim: Date; // LocalDateTime
	idProfessor: number; // Long
	idAluno: number; // Long
}

