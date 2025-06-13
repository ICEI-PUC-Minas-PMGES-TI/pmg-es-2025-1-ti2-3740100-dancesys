import { HttpClient } from "@angular/common/http";
import { inject, Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { environment } from "../../environment/environment";
import { Sala } from "../models/Sala.model";
@Injectable({
	providedIn: "root",
})
export class SalaService {
	http = inject(HttpClient);

	url = `${environment.API_URL}sala`
	public fetchSalas(): Observable<Sala[]> {
		return this.http.get(
			`${this.url}/buscar`,
		) as Observable<Sala[]>;
	}

	public salvarSala(item: Sala) {
			return this.http.post(`${this.url}`, { ...item});
		}
	
	public excluir(id: number){
		return this.http.delete(`${this.url}/excluir/${id}`)
	}
}
