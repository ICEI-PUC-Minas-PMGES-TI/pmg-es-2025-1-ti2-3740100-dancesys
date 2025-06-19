export interface AulaExtraFilter {
	dataInicio?: string | Date;
	dataFim?: string | Date;
	idProfessor?: number | Date;
	idAluno?: number;
	status?: number[];
	pagina?: number;
	tamanho?: number;
	orderBy?: string;
	order?: string;
}
