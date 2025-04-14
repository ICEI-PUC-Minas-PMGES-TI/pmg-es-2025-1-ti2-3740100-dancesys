import { HttpClient } from "@angular/common/http";
import { inject, Injectable } from "@angular/core";
import { Usuario } from "../models/usuario.model";
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
		this.http
			.post(`${environment.API_URL}usuario/aluno`, aluno)
			.subscribe();
	}
}
