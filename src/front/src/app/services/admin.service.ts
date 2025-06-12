import { HttpClient, HttpParams } from "@angular/common/http";
import { inject, Injectable } from "@angular/core";
import { lastValueFrom, Observable, take } from "rxjs";
import { environment } from "../../environment/environment";
import { UsuarioFiltro } from "../models/usuario.model";
import { horarioProfessorFilter } from "../models/horarioProfessorFilter.model";
import { HorarioProfessor } from "../models/horarioProfessor.model";
import { Aluno, AlunoFilter } from "../models/aluno.model";
import { Professor, ProfessorFiltro } from "../models/professor.model";
import { DividendoFilter, Dividendo } from "../models/Dividendo.model";
import { Aula, AulaFilter } from "../models/Aula.model";
import { AulaOcorrenciaFilter } from "../models/AulaOcorrencia.model";
import { Evento, EventoFilter, EventoResponse } from "../models/evento.model";
import { Mensagem } from "../models/Mensagem.model";
import {
	ApresentacaoEvento,
	ApresentacaoEventoFilter,
	ApresentacaoEventoResponse,
} from "../models/apresentacao_evento.model";
import { Ensaio, EnsaioFilter } from "../models/Ensaio.model";
import { FormAlunoValue } from "../pages/Admin/main-admin-page/usuarios-admin-page/aluno-tabela-admin-page/aluno-tabela-admin-page.component";
import { FormProfessorValue } from "../pages/Admin/main-admin-page/usuarios-admin-page/professor-tabela-admin-page/professor-tabela-admin-page.component";

export interface AlunoResponse {
	id: number;
	creditos: number;
	boolBaile: boolean;
	tipo: number;
	idUsuario: {
		id: number;
		nome: string;
		cpf: string;
		numero: string;
		email: string;
		senha: string;
		tipo: 3;
		status: boolean;
		endereco: string;
		urlFoto: null | string;
		dataNascimento: Date;
		criadoEm: Date;
	};
	modalidades: {
		id: {
			idAluno: number;
			idModalidade: number;
		};
		idModalidade: {
			id: number;
			nome: string;
		};
		nivel: 1 | 2 | 3;
	}[];
}

export interface ProfessorResponse {
	id: number;
	informacoesProfissionais: string;
	valorHoraExtra: number;
	idUsuario: {
		id: number;
		nome: string;
		cpf: string;
		numero: string;
		email: string;
		senha: string;
		tipo: 2;
		status: boolean;
		endereco: string;
		urlFoto: null | string;
		dataNascimento: Date;
		criadoEm: Date;
	};
	modalidades: {
		id: {
			idProfessor: number;
			idModalidade: number;
		};
		idModalidade: {
			id: number;
			nome: string;
		};
	}[];
}

@Injectable({
	providedIn: "root",
})
export class AdminService {
	http = inject(HttpClient);

	public fetchAlunos(filtro: AlunoFilter): Observable<{
		conteudo: AlunoResponse[];
		total: number;
	}> {
		return this.http.post(`${environment.API_URL}usuario/aluno/buscar`, {
			...filtro,
		}) as Observable<{ conteudo: AlunoResponse[]; total: number }>;
	}

	public fetchProfessores(filtro?: ProfessorFiltro): Observable<{
		conteudo: ProfessorResponse[];
		total: number;
	}> {
		console.log(filtro);
		return this.http.post(
			`${environment.API_URL}usuario/professor/buscar`,
			{ ...filtro },
		) as Observable<{ conteudo: ProfessorResponse[]; total: number }>;
	}

	public addUsuarioAluno(aluno: FormAlunoValue) {
		return this.http.post(`${environment.API_URL}usuario/aluno`, aluno);
	}

	public addUsuarioProfessor(professor: FormProfessorValue) {
		return this.http.post(`${environment.API_URL}usuario/professor`, {
			valorHoraExtra: professor.valorHoraExtra,
			informacoesProfissionais: professor.informacoesProfissionais,
			modalidades: [...professor.modalidades],
			usuario: {
				nome: professor.nome,
				endereco: professor.endereco,
				cpf: professor.cpf,
				numero: professor.numero,
				tipo: professor.tipo,
				urlFoto: null,
				dataNascimento: professor.dataNascimento,
				email: professor.email,
			},
		});
	}

	public filterAlunos(
		filtro: AlunoFilter,
	): Observable<{ conteudo: AlunoResponse[]; total: number }> {
		return this.http.post(`${environment.API_URL}usuario/aluno/buscar`, {
			...filtro,
		}) as Observable<{ conteudo: AlunoResponse[]; total: number }>;
	}

	public filterProfessores(
		filtro: ProfessorFiltro,
	): Observable<{ conteudo: ProfessorResponse[]; total: number }> {
		return this.http.post(
			`${environment.API_URL}usuario/professor/buscar`,
			{ ...filtro },
		) as Observable<{ conteudo: ProfessorResponse[]; total: number }>;
	}

	// deve ser o ID da tabela Usuarios
	public toggleUserStatus(id: number) {
		return this.http.get(`${environment.API_URL}usuario/status/${id}`);
	}

	public editarAluno(aluno: Aluno) {
		const a = { ...aluno, usuario: aluno.idUsuario };
		return this.http.post(`${environment.API_URL}usuario/aluno/alterar`, {
			...a,
			boolBaile: aluno.boolBaile ? 1 : 0,
			status: aluno.idUsuario.status,
			senha: aluno.idUsuario.senha,
			criadoEm: aluno.idUsuario.criadoEm,
			urlFoto: aluno.idUsuario.urlFoto,
		});
	}

	public editarProfessor(professor: Professor) {
		const p = { ...professor, usuario: professor.idUsuario };
		return this.http.post(
			`${environment.API_URL}usuario/professor/alterar`,
			{
				...p,
			},
		);
	}

	public fetchHoraioProfessor(filtro: horarioProfessorFilter) {
		return this.http.post(`${environment.API_URL}horario/buscar`, {
			...filtro,
		});
	}

	public excluirHorarioProfessor(id: number) {
		return this.http.delete(`${environment.API_URL}horario/excluir/${id}`, {
			responseType: "text",
		});
	}

	public salvarHorarioProfessor(item: HorarioProfessor) {
		return this.http.post(`${environment.API_URL}horario`, { ...item });
	}

	public filterDividendos(filtro: DividendoFilter) {
		return this.http.post(`${environment.API_URL}dividendo/buscar`, {
			...filtro,
		});
	}

	public filterAulas(filtro: AulaFilter) {
		return this.http.post(`${environment.API_URL}aula/buscar`, {
			...filtro,
		});
	}

	public addAula(item: Aula) {
		return this.http.post(`${environment.API_URL}aula`, { ...item });
	}

	public alterarStatusAula(id: number) {
		return this.http.get(`${environment.API_URL}aula/status/${id}`);
	}

	public fetchEventos(filtro: EventoFilter): Observable<EventoResponse> {
		return this.http.post<EventoResponse>(
			`${environment.API_URL}evento/buscar`,
			{
				...filtro,
			},
		);
	}

	// caso nao exista ele cria um
	public updateEvento(evento: Evento) {
		return this.http.post(`${environment.API_URL}evento`, { ...evento });
	}

	public excluirEvento(id: number) {
		return this.http.delete(`${environment.API_URL}evento/excluir/${id}`);
	}

	public toogleStatusAula(id: number) {
		return this.http.get(`${environment.API_URL}aula/status/${id}`);
	}

	public fetchAulasOcorrentes(filtro: AulaOcorrenciaFilter, geral?: boolean) {
		if (geral) {
			return this.http.post(
				`${environment.API_URL}aula/ocorrencia/buscar/geral`,
				{
					...filtro,
					dataInicio: filtro.dataInicio
						.toISOString()
						.substring(0, 10),
					dataFim: filtro.dataFim.toISOString().substring(0, 10),
				},
			);
		}
		return this.http.post(`${environment.API_URL}aula/ocorrencia/buscar`, {
			...filtro,
			dataInicio: filtro.dataInicio.toISOString().substring(0, 10),
			dataFim: filtro.dataFim.toISOString().substring(0, 10),
		});
	}

	public fetchAulasOcorrentes2(filtro: AulaOcorrenciaFilter) {
		return this.http.post(`${environment.API_URL}aula/ocorrencia/buscar`, {
			...filtro,
		});
	}

	public fetchApresentacoes(
		filtro: ApresentacaoEventoFilter,
	): Observable<ApresentacaoEventoResponse> {
		return this.http.post<ApresentacaoEventoResponse>(
			`${environment.API_URL}apresentacaoEvento/buscar`,
			{
				...filtro,
			},
		);
	}

	// cria caso não exista, editar caso exista
	public updateApresentacaoEvento(apresentacao: ApresentacaoEvento) {
		return this.http.post(`${environment.API_URL}apresentacaoEvento`, {
			...apresentacao,
		});
	}

	public deleteApresentacaoEvento(id: number) {
		return this.http.delete(
			`${environment.API_URL}apresentacaoEvento/excluir/${id}`,
		);
	}

	public cancelarAulaOcorrente(mensgaem: Mensagem, id: number) {
		return this.http.post(
			`${environment.API_URL}aula/ocorrencia/cancelar/${id}`,
			{ ...mensgaem },
		);
	}

	public filterEnsaio(filtro: EnsaioFilter) {
		return this.http.post(
			`${environment.API_URL}ensaioApresentacao/buscar`,
			{
				...filtro,
				dataInicio: filtro.dataInicio.toISOString().substring(0, 10),
				dataFim: filtro.dataFim.toISOString().substring(0, 10),
			},
		);
	}

	public filterEnsaio2(filtro: EnsaioFilter) {
		return this.http.post(
			`${environment.API_URL}ensaioApresentacao/buscar`,
			{
				...filtro,
			},
		);
	}

	public addEnsaio(item: Ensaio) {
		return this.http.post(`${environment.API_URL}ensaioApresentacao`, {
			...item,
		});
	}

	public deleteEnsaio(id: number) {
		return this.http.delete(
			`${environment.API_URL}ensaioApresentacao/excluir/${id}`,
		);
	}
}
