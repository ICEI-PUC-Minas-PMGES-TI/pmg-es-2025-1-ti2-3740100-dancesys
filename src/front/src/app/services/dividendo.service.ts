import { HttpClient } from "@angular/common/http";
import { inject, Injectable } from "@angular/core";
import { environment } from "../../environment/environment";

@Injectable({
	providedIn: "root",
})
export class DividendoService {
	http = inject(HttpClient);

    url: string = `${environment.API_URL}dividendo/`

	public pagarBoleto(id: number){
        return this.http.get(`${this.url}pagar/${id}`);
    }
}