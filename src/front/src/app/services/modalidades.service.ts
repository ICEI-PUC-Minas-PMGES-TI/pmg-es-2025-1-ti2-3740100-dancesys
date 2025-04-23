import { HttpClient } from "@angular/common/http";
import { inject, Injectable } from "@angular/core";
import { Modalidade } from "../models/modalidade.model";
import { Observable } from "rxjs";
import { environment } from "../../environment/environment";
@Injectable({
	providedIn: "root",
})
export class ModalidadesService {
	http = inject(HttpClient);

	public fetchModalidades(): Observable<Modalidade[]> {
		return this.http.get(
			`${environment.API_URL}modalidade/buscar`,
		) as Observable<Modalidade[]>;
	}
}
