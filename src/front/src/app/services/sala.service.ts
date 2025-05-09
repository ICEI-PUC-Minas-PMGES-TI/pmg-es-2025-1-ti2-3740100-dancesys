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

	public fetchSalas(): Observable<Sala[]> {
		return this.http.get(
			`${environment.API_URL}sala/buscar`,
		) as Observable<Sala[]>;
	}
}
