import { HttpClient } from "@angular/common/http";
import { inject, Injectable } from "@angular/core";
import { Usuario } from "../models/usuario.model";
import { Observable } from "rxjs";
@Injectable({
	providedIn: "root",
})
export class AdminService {
	http = inject(HttpClient);

	public fetchUsuarios(): Observable<Usuario[]> {
		return this.http.get(
			"http://localhost:8080/usuario/buscar",
		) as Observable<Usuario[]>;
	}
}
