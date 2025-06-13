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

	url = `${environment.API_URL}modalidade`

	public fetchModalidades(): Observable<Modalidade[]> {
		return this.http.get(
			`${this.url}/buscar`,
		) as Observable<Modalidade[]>;
	}

	public salvarModalidade(item: Modalidade) {
		return this.http.post(`${this.url}`, { ...item});
	}

	public excluir(id: number){
		return this.http.delete(`${this.url}/excluir/${id}`)
	}

}
