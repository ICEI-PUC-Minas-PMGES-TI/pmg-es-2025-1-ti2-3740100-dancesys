import { HttpClient, HttpParams } from "@angular/common/http";
import { inject, Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { environment } from "../../environment/environment";
import { UsuarioFiltro } from "../models/usuario.model";
import { FormAlunoValue } from "../pages/Admin/main-admin-page/usuarios-admin-page/usuarios-admin-page.component";

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

@Injectable({
	providedIn: "root",
})
export class AdminService {
	http = inject(HttpClient);

	public fetchAlunos(): Observable<AlunoResponse[]> {
		return this.http.get(
			`${environment.API_URL}usuario/aluno/buscar`
		) as Observable<AlunoResponse[]>;
	}

	public addUsuarioAluno(aluno: FormAlunoValue) {
		return this.http.post(`${environment.API_URL}usuario/aluno`, aluno);
	}

	public filterUsuarios(filtro: UsuarioFiltro): Observable<AlunoResponse[]> {
		const params = new HttpParams({
			fromObject: { ...filtro },
		});
		return this.http.get<AlunoResponse[]>(
			`${
				environment.API_URL
			}usuario/aluno/aluno/buscar?${params.toString()}`
		) as Observable<AlunoResponse[]>;
	}

	// deve ser o ID da tabela Usuarios
	public toggleUserStatus(id: number) {
		return this.http.get(`${environment.API_URL}usuario/status/${id}`);
	}

	public editarAluno(aluno: FormAlunoValue) {
		return this.http.post(
			`${environment.API_URL}usuario/aluno/alterar`,
			aluno
		);
	}
}
