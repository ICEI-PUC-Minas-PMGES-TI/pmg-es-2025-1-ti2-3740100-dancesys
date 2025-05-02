import { HttpClient, HttpParams } from "@angular/common/http";
import { inject, Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { environment } from "../../environment/environment";
import { UsuarioFiltro } from "../models/usuario.model";
import { horarioProfessorFilter } from "../models/horarioProfessorFilter.model";
import { HorarioProfessor } from "../models/horarioProfessor.model";
import {
	FormAlunoValue,
	FormProfessorValue,
} from "../pages/Admin/main-admin-page/usuarios-admin-page/usuarios-admin-page.component";
import { Aluno } from "../models/aluno.model";
import { Professor } from "../models/professor.model";
import { DividendoFilter, DividendoResponse } from "../models/Dividendo.model";

export type AlunoResponse = {
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
};
export type ProfessorResponse = {
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
};

@Injectable({
	providedIn: "root",
})
export class AdminService {
	http = inject(HttpClient);

	public fetchAlunos(): Observable<AlunoResponse[]> {
		return this.http.get(
			`${environment.API_URL}usuario/aluno/buscar`,
		) as Observable<AlunoResponse[]>;
	}

	public fetchProfessores(): Observable<ProfessorResponse[]> {
		return this.http.get(
			`${environment.API_URL}usuario/professor/buscar`,
		) as Observable<ProfessorResponse[]>;
	}

	public addUsuarioAluno(aluno: FormAlunoValue) {
		return this.http.post(`${environment.API_URL}usuario/aluno`, aluno);
	}

	public addUsuarioProfessor(professor: FormProfessorValue) {
		console.log(professor);
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

	public filterUsuarios(filtro: UsuarioFiltro): Observable<AlunoResponse[]> {
		const params = new HttpParams({
			fromObject: { ...filtro },
		});
		return this.http.get<AlunoResponse[]>(
			`${
				environment.API_URL
			}usuario/aluno/buscar?${params.toString()}`,
		) as Observable<AlunoResponse[]>;
	}

	// deve ser o ID da tabela Usuarios
	public toggleUserStatus(id: number) {
		return this.http.get(`${environment.API_URL}usuario/status/${id}`);
	}

	public editarAluno(aluno: Aluno) {
		return this.http.post(`${environment.API_URL}usuario/aluno/alterar`, {
			...aluno,
			boolBaile: aluno.boolBaile ? 1 : 0,
			status: aluno.usuario.status,
			senha: aluno.usuario.senha,
			criadoEm: aluno.usuario.criadoEm,
			urlFoto: aluno.usuario.urlFoto,
		});
	}

	public editarProfessor(professor: Professor) {
		return this.http.post(
			`${environment.API_URL}usuario/professor/alterar`,
			{
				...professor,
			},
		);
	}

	public fetchHoraioProfessor(filtro: horarioProfessorFilter){
		return this.http.post(`${environment.API_URL}horario/buscar`,
			{
				...filtro,
			},);

	}

	public excluirHorarioProfessor(id: number){
		return this.http.delete(`${environment.API_URL}horario/excluir/${id}`, { responseType: 'text' });
	}	  

	public salvarHorarioProfessor(item: HorarioProfessor){
		return this.http.post(`${environment.API_URL}horario`,{...item});
	}

	public filterDividendos(filtro: DividendoFilter){
		return this.http.post(`${environment.API_URL}dividendo/buscar`,
			{
				...filtro,
			},);
	}
}
