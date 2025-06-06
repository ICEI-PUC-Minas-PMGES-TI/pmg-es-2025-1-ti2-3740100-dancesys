import { HttpClient } from "@angular/common/http";
import { inject, Injectable } from "@angular/core";
import { environment } from "../../environment/environment";
import { AulaExtraFilter } from "../models/AulaExtra.model";
import { AulaExtraDTO } from "../models/Aula.model";

@Injectable({
	providedIn: "root",
})
export class AulaService {
	http = inject(HttpClient);

	url: string = `${environment.API_URL}aula/`;

	public filterAulaExtra(filtro: AulaExtraFilter) {
		return this.http.post(`${this.url}extra/buscar`, {
			...filtro,
		});
	}

	public solicitarAulaExtra(aula: AulaExtraDTO) {
		return this.http.post(`${this.url}extra`, { ...aula });
	}
}

