import { HttpClient, HttpParams } from "@angular/common/http";
import { inject, Injectable } from "@angular/core";
import { Usuario, UsuarioFiltro, UsuarioTipos } from "../models/usuario.model";
import { Observable } from "rxjs";
import { environment } from "../../environment/environment";
import { FormAlunoValue } from "../pages/Admin/main-admin-page/usuarios-admin-page/usuarios-admin-page.component";
@Injectable({
	providedIn: "root",
})
export class AdminService {
	http = inject(HttpClient);

	public fetchUsuarios(): Observable<Usuario[]> {
		return this.http.get(
			`${environment.API_URL}usuario/buscar`,
		) as Observable<Usuario[]>;
	}

	public addUsuarioAluno(aluno: FormAlunoValue) {
		return this.http.post(`${environment.API_URL}usuario/aluno`, aluno);
	}

	public filterUsuarios(filtro: UsuarioFiltro) {
		const params = new HttpParams({
			fromObject: { ...filtro },
		});
		return this.http.get(
			`${environment}usuario/buscar?${params.toString()}`,
		);
	}
}
